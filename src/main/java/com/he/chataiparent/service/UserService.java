package com.he.chataiparent.service;

import com.he.chataiparent.model.vo.EditUserVO;
import com.he.chataiparent.model.vo.UserVO;
import com.he.chataiparent.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.he.chataiparent.common.result.Result;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author he
* @description 针对表【user】的数据库操作Service
* @createDate 2023-11-17 15:41:04
*/
public interface UserService extends IService<User> {

    /**
     * 登录
     * @param userVO
     * @return
     */
    Result login(UserVO userVO, HttpServletRequest request);

    /**
     * 注册
     * @param user
     * @return
     */
    Result register(User user);

    /**
     * 修改用户
     * @param editUserVO
     * @return
     */
    Result updateUser(EditUserVO editUserVO);

    /**
     * 校验密码
     * @param userVO
     * @return
     */
    Result checkPassword(UserVO userVO);
}
