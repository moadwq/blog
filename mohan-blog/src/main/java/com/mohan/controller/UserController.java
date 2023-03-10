package com.mohan.controller;

import com.mohan.annotation.SystemLog;
import com.mohan.entity.User;
import com.mohan.service.UserService;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult getUserInfo(){
        ResponseResult result = userService.getUserInfo();
        return result;
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        ResponseResult result = userService.updateUserInfo(user);
        return result;
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        ResponseResult result = userService.register(user);
        return result;
    }
}
