package com.he.chataiparent.common.auth;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.he.chataiparent.common.constant.RedisConst;
import com.he.chataiparent.common.exception.ChatException;
import com.he.chataiparent.common.result.Result;
import com.he.chataiparent.common.result.ResultCode;
import com.he.chataiparent.model.vo.UserInfoVO;
import com.he.chataiparent.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;
    public LoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String shortToken = request.getHeader("shortToken");
        String longToken = request.getHeader("longToken");

        if (StrUtil.isBlank(shortToken) && StrUtil.isBlank(longToken)) {
            throw new ChatException(ResultCode.UNAUTHORIZED);
        }

        // 判断短token是否有效
        if (StrUtil.isNotBlank(shortToken)) {
            try {
                // 获取userId
                Long userId = JwtHelper.getUserId(shortToken);
                // 根据userId从Redis中获取用户信息
                UserInfoVO userInfo = (UserInfoVO) redisTemplate.opsForValue().get(RedisConst.USER_LOGIN_KEY_PREFIX + userId);
                if (userId == null || userInfo == null) {
                    throw new ChatException(401, "无效的短token");
                }

                // 将数据存放到ThreadLocal中
                AuthContextHolder.setUserId(userId);
                AuthContextHolder.setUserInfo(userInfo);
                return true;
            } catch (Exception e) {
                log.debug("短期token已过期");
            }

            // 判断长token是否有效
            try {
                // 获取userId
                Long userId = JwtHelper.getUserId(longToken);
                // 根据userId从Redis中获取用户信息
                UserInfoVO userInfo = (UserInfoVO) redisTemplate.opsForValue().get(RedisConst.USER_LOGIN_KEY_PREFIX + userId);
                if (userId == null || userInfo == null) {
                    throw new ChatException(401, "无效的长token");
                }

                // 将数据存放到ThreadLocal中
                AuthContextHolder.setUserId(userId);
                AuthContextHolder.setUserInfo(userInfo);

                // 刷新短token
                Map<String, String> map = JwtHelper.refresh(longToken);
                map.forEach(response::setHeader);
                // 更新Redis中的缓存
                redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX + userId,
                        userInfo,
                        RedisConst.USER_LOGIN_TIMEOUT,
                        TimeUnit.SECONDS);
                return true;
            } catch (Exception e) {
                log.error(e.getClass() + ": " + e.getMessage());
                response.getWriter().write(JSON.toJSONString(Result.build(null, ResultCode.UNAUTHORIZED)));
                return false;
            }
        }
        return true;
    }

}
