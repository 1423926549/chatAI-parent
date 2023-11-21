package com.he.chataiparent.common.auth;

import com.he.chataiparent.model.vo.UserInfoVO;
import lombok.Getter;

public class AuthContextHolder {

    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();
    private static ThreadLocal<UserInfoVO> userInfo = new ThreadLocal<>();

    public static void clear(){
        userId.remove();
        userInfo.remove();
    }

    public static Long getUserId(){
        return userId.get();
    }

    public static void setUserId(Long _userId){
        userId.set(_userId);
    }

    public static UserInfoVO getUserInfo(){
        return userInfo.get();
    }

    public static void setUserInfo(UserInfoVO _userInfo) {
        userInfo.set(_userInfo);
    }
}
