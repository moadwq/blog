package com.mohan.controller;

import com.mohan.entity.User;
import com.mohan.service.BlogLoginService;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        ResponseResult result = blogLoginService.login(user);
        return result;
    }
}
