package com.he.chataiparent.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.chataiparent.common.auth.AuthContextHolder;
import com.he.chataiparent.common.constant.RedisConst;
import com.he.chataiparent.mapper.UserMapper;
import com.he.chataiparent.model.vo.EditUserVO;
import com.he.chataiparent.model.vo.UserInfoVO;
import com.he.chataiparent.model.vo.UserVO;
import com.he.chataiparent.model.entity.User;
import com.he.chataiparent.common.result.Result;
import com.he.chataiparent.service.UserService;
import com.he.chataiparent.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
* @author he
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-11-17 15:41:04
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录
     * @param userVO
     * @param request
     * @return
     */
    @Override
    public Result login(UserVO userVO, HttpServletRequest request) {
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = this.getOne(wrapper);
        if (user == null) {
            return Result.fail("用户名不存在");
        }
        password = DigestUtil.md5Hex(password);
        if (!password.equals(user.getPassword())) {
            return Result.fail("密码错误");
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(user, userInfoVO);

        String shortToken = JwtHelper.createShortToken(user.getId(), user.getUsername());
        String longToken = JwtHelper.createLongToken(user.getId(), user.getUsername());
        redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX + user.getId(),
                                        userInfoVO,
                                        RedisConst.USER_LOGIN_TIMEOUT,
                                        TimeUnit.SECONDS);

        Map<String, Object> map = new HashMap<>();
        map.put("shortToken", shortToken);
        map.put("longToken", longToken);
        map.put("userInfo", userInfoVO);

        return Result.ok(map);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public Result register(User user) {
        String username = user.getUsername();
        if (this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) != null) {
            return Result.fail("用户名已存在");
        }
        String password = user.getPassword();
        password = DigestUtil.md5Hex(password);
        user.setPassword(password);
        // TODO 暂时使用默认头像
        user.setHeader("https://chat-he.oss-cn-hangzhou.aliyuncs.com/default_handsome.jpg");
        this.save(user);
        return Result.ok("注册成功");
    }

    /**
     * 修改用户
     *
     * @param editUserVO
     * @return
     */
    @Override
    public Result updateUser(EditUserVO editUserVO) {
        // 判断用户是否存在
        String username = editUserVO.getUsername();
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (user == null) {
            return Result.fail("用户不存在");
        }
        // 判断原密码是否正确
        String oldPassword = DigestUtil.md5Hex(editUserVO.getOldPassword());
        if (!oldPassword.equals(user.getPassword())) {
            return Result.fail("原密码错误");
        }
        // 修改密码
        String password = editUserVO.getNewPassword();
        password = DigestUtil.md5Hex(password);
        user.setPassword(password);
        user.setNickname(editUserVO.getNickname());
        this.updateById(user);

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(user, userInfoVO);

        return Result.ok(userInfoVO);
    }

    /**
     * 校验密码
     *
     * @param userVO
     * @return
     */
    @Override
    public Result checkPassword(UserVO userVO) {
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        password = DigestUtil.md5Hex(password);
        wrapper.eq(User::getPassword, password);
        User user = this.getOne(wrapper);

        return Result.ok(user != null);

    }

    /**
     *
     */
    @Override
    public void logout() {
        Long userId = AuthContextHolder.getUserId();
        redisTemplate.delete(RedisConst.USER_LOGIN_KEY_PREFIX + userId);
        AuthContextHolder.clear();
    }
}




