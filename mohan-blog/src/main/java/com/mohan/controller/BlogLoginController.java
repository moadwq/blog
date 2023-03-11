package com.mohan.controller;

import com.mohan.annotation.SystemLog;
import com.mohan.domain.dto.UserLoginDto;
import com.mohan.domain.entity.User;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.exception.SystemException;
import com.mohan.service.BlogLoginService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "用户登录登出接口")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "用户登录")
    @ApiOperation(value = "用户登录")
    public ResponseResult login(@RequestBody UserLoginDto userLoginDto){
        if (!StringUtils.hasText(userLoginDto.getUserName())){
            // 提示必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        User user = BeanCopyUtils.copyBean(userLoginDto, User.class);
        ResponseResult result = blogLoginService.login(user);
        return result;
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "用户登出")
    @ApiOperation(value = "用户登出")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
