package com.mohan.service;

import com.mohan.utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 头像图片上传
     */
    ResponseResult img(MultipartFile img);
}
