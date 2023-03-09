package com.mohan.controller;

import com.mohan.service.LinkService;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    // 获取全部友链
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        ResponseResult result = linkService.getAllLink();
        return result;
    }
}
