package com.mohan.utils;

import com.mohan.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 判断是否是管理员，默认id为1的是管理员
     */
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
