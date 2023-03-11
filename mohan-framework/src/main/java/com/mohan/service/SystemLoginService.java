package com.mohan.service;

import com.mohan.domain.entity.User;
import com.mohan.utils.ResponseResult;

public interface SystemLoginService {
    /**
     * 用户登录
     * @param user 用户信息
     */
    ResponseResult login(User user);

}
