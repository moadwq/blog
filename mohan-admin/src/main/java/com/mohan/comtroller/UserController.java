package com.mohan.comtroller;

import com.mohan.domain.dto.UserPageDto;
import com.mohan.service.UserService;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult list(UserPageDto userPageDto){
        ResponseResult result = userService.pageList(userPageDto);
        return result;
    }

}
