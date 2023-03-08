package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.entity.Article;
import com.mohan.utils.ResponseResult;

import java.util.List;

public interface ArticleService extends IService<Article> {

    /**
     * 查询热门文章
     * @return 封装热门文章的响应对象
     */
    ResponseResult<List<Article>> hotArticleList();
}
