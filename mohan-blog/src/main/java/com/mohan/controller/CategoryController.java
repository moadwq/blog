package com.mohan.controller;

import com.mohan.annotation.SystemLog;
import com.mohan.service.CategoryService;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(description = "文章类型接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "获得文章类型列表")
    @ApiOperation(value = "获得文章类型列表")
    public ResponseResult getCategoryList(){
        ResponseResult result = categoryService.getCategoryList();
        return result;
    }

}
