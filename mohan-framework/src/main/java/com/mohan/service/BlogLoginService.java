package com.mohan.service;

import com.mohan.domain.entity.User;
import com.mohan.utils.ResponseResult;

public interface BlogLoginService {
    /**
     * 用户登录
     * @param user 用户信息
     * @return
     */
    ResponseResult login(User user);

    /**
     * 退出登录
     * @return
     */
    ResponseResult logout();
}
