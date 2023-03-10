package com.mohan.controller;

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
    public ResponseResult updateUserInfo(@RequestBody User user){
        ResponseResult result = userService.updateUserInfo(user);
        return result;
    }
}
