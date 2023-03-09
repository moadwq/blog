package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.SystemConstants;
import com.mohan.entity.Article;
import com.mohan.mapper.ArticleMapper;
import com.mohan.service.ArticleService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import com.mohan.vo.ArticleListVo;
import com.mohan.vo.HotArticleVo;
import com.mohan.vo.PageVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public ResponseResult hotArticleList() {
        // 封装查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多查10条消息
        IPage<Article> page = new Page<>(1,10);
        page(page, queryWrapper);// 分页
        List<Article> articles = page.getRecords();  // 获取查询结果

        // 类型转换
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult ArticleList(Long categoryId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> articleQuery = new LambdaQueryWrapper<>();
        // 判断 categoryId 是否不为空，不为空则 根据 类型id查
        articleQuery.eq(Objects.nonNull(categoryId) && categoryId > 0,
                                 Article::getCategoryId,
                                 categoryId);
        // 文章状态是发布的
        articleQuery.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 文章置顶，降序排序
        articleQuery.orderByDesc(Article::getIsTop);
        // 分页
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,articleQuery);
        // 获取并封装查询结果
        List<Article> articles = page.getRecords();
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
