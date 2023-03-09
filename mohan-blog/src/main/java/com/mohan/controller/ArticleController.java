package com.mohan.controller;

import com.mohan.entity.Article;
import com.mohan.service.ArticleService;
import com.mohan.utils.ResponseResult;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 热门文章列表
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        // 查询热门文章，封装返回
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
    // 文章列表
    @GetMapping("/articleList")
    public ResponseResult articleList(Long categoryId, Integer pageNum, Integer pageSize){
        ResponseResult result = articleService.ArticleList(categoryId,pageNum,pageSize);
        return result;
    }

    // 文章正文详情页
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        ResponseResult result = articleService.getArticleDetail(id);
        return result;
    }
}
