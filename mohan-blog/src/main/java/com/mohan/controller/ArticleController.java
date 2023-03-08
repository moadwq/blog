package com.mohan.controller;

import com.mohan.entity.Article;
import com.mohan.service.ArticleService;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult<List<Article>> hotArticleList(){
        // 查询热门文章，封装返回
        ResponseResult<List<Article>> result = articleService.hotArticleList();
        return result;
    }
}
