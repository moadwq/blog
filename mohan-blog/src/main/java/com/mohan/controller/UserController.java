package com.mohan.controller;

import com.mohan.annotation.SystemLog;
import com.mohan.domain.dto.RegisterUserDto;
import com.mohan.domain.dto.UpdateUserDto;
import com.mohan.domain.entity.User;
import com.mohan.service.UserService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "获得用户信息")
    @ApiOperation(value = "获得用户信息")
    public ResponseResult getUserInfo(){
        ResponseResult result = userService.getUserInfo();
        return result;
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    @ApiOperation(value = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody UpdateUserDto updateUserDto){
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        ResponseResult result = userService.updateUserInfo(user);
        return result;
    }

    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    @ApiOperation(value = "用户注册")
    public ResponseResult register(@RequestBody RegisterUserDto registerUserDto){
        User user = BeanCopyUtils.copyBean(registerUserDto, User.class);
        ResponseResult result = userService.register(user);
        return result;
    }
}
