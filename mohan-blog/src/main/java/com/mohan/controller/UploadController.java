package com.mohan.controller;

import com.mohan.annotation.SystemLog;
import com.mohan.service.UploadService;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(description = "上传文件相关接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation(value = "头像上传")
    public ResponseResult uploadImg(MultipartFile img){
        ResponseResult result = uploadService.img(img);
        return result;
    }
}
