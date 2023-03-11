package com.mohan.controller;

import com.mohan.annotation.SystemLog;
import com.mohan.service.ArticleService;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Api(description = "文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 热门文章列表
    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "获得热门文章列表")
    @ApiOperation(value = "获得热门文章列表")
    public ResponseResult hotArticleList(){
        // 查询热门文章，封装返回
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
    // 文章列表
    @GetMapping("/articleList")
    @SystemLog(businessName = "获得文章列表")
    @ApiOperation(value = "获得文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "文章类型id"),
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小")
    })
    public ResponseResult articleList(Long categoryId, Integer pageNum, Integer pageSize){
        ResponseResult result = articleService.ArticleList(categoryId,pageNum,pageSize);
        return result;
    }

    // 文章正文详情页
    @GetMapping("/{id}")
    @SystemLog(businessName = "文章正文详情页")
    @ApiOperation(value = "查询文章正文详情页")
    @ApiImplicitParam(name = "id",value = "文章id")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        ResponseResult result = articleService.getArticleDetail(id);
        return result;
    }

    // 更新浏览量
    @PutMapping("/updateViewCount/{id}")
    @SystemLog(businessName = "更新浏览量")
    @ApiOperation(value = "更新浏览量")
    @ApiImplicitParam(name = "id",value = "文章id")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        ResponseResult result = articleService.updateViewCount(id);
        return result;
    }
}
