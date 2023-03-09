package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.entity.Article;
import com.mohan.utils.ResponseResult;

import java.util.List;

public interface ArticleService extends IService<Article> {

    /**
     * 查询热门文章
     * @return 封装了热门文章的响应对象
     */
    ResponseResult hotArticleList();

    /**
     * 查询文章列表
     * @param categoryId 文章分类id
     * @param pageNum 页码
     * @param pageSize  页面大小
     * @return  封装了文章列表的响应对象
     */
    ResponseResult ArticleList(Long categoryId, Integer pageNum, Integer pageSize);

    /**
     * 获取文章详情
     * @param id 文章id
     */
    ResponseResult getArticleDetail(Long id);
}
