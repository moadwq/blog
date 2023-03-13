package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.domain.dto.ArticleDto;
import com.mohan.domain.dto.ArticleListDto;
import com.mohan.domain.entity.Article;
import com.mohan.utils.ResponseResult;

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

    /**
     * 更新文章浏览量
     * @param id 文章id
     */
    ResponseResult updateViewCount(Long id);

    /**
     * 添加文章
     */
    ResponseResult addArticle(ArticleDto articleDto);

    /**
     * 按条件分页查询文章列表
     */
    ResponseResult pageList(ArticleListDto articleListDto);

    /**
     * 获取文章详细信息
     * @param id 文章id
     */
    ResponseResult getArticle(Long id);
}
