package com.mohan.controller;

import com.mohan.annotation.SystemLog;
import com.mohan.service.LinkService;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Api(description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

    // 获取全部友链
    @GetMapping("/getAllLink")
    @SystemLog(businessName = "获得友链列表")
    @ApiOperation(value = "获得友链列表")
    public ResponseResult getAllLink(){
        ResponseResult result = linkService.getAllLink();
        return result;
    }
}
