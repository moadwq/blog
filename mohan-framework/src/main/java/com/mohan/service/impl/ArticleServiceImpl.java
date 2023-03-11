package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.SystemConstants;
import com.mohan.entity.Article;
import com.mohan.mapper.ArticleMapper;
import com.mohan.service.ArticleService;
import com.mohan.service.CategoryService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.RedisCache;
import com.mohan.utils.ResponseResult;
import com.mohan.vo.ArticleDetailVo;
import com.mohan.vo.ArticleListVo;
import com.mohan.vo.HotArticleVo;
import com.mohan.vo.PageVo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;

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
        // 获取查询结果
        List<Article> articles = page.getRecords();

        // 从redis中查询实际浏览量并替换
        replaceViewCount(articles);

        articles = articles.parallelStream()
                // 根据每个 article的分类id，查询分类名字，并设置
                .peek(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        // 类型转换
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        // 封装到 Page 对象中
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }



    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据文章id查询文章信息
        Article article = getById(id);
        // 根据分类id查询分类名
        String name = categoryService.getById(article.getCategoryId()).getName();
        article.setCategoryName(name);
        // 在redis中查询文章浏览量
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNT_KEY, id.toString());
        article.setViewCount(viewCount.longValue());
        // 类型转换
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新redis中对应的浏览量
        redisCache.incrementMapValue(SystemConstants.ARTICLE_VIEWCOUNT_KEY,id.toString(),1);
        return ResponseResult.okResult();
    }

    /**
     * 从redis中查询实时浏览量，并替换
     * @param articles 需要替换浏览量的文章列表
     */
    private void replaceViewCount(List<Article> articles) {
        // 从redis获取文章浏览量
        Map<String, Integer> map = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEWCOUNT_KEY);
        // 类型转换
        List<Article> viewCounts = map.entrySet().parallelStream()
                .map(entry -> {
                    Article article = new Article();
                    article.setId(Long.parseLong(entry.getKey()));
                    article.setViewCount(entry.getValue().longValue());
                    return article;
                })
                .collect(Collectors.toList());
        // 遍历替换 文章浏览量
        for (Article article : articles) {
            for (Article viewCount : viewCounts) {
                if (article.getId() == viewCount.getId()){
                    article.setViewCount(viewCount.getViewCount());
                }
            }
        }
    }
}
