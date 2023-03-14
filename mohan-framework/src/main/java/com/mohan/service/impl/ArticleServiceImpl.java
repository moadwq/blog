package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.SystemConstants;
import com.mohan.domain.dto.ArticleDto;
import com.mohan.domain.dto.ArticleListDto;
import com.mohan.domain.entity.Article;
import com.mohan.domain.entity.ArticleTag;
import com.mohan.domain.entity.Category;
import com.mohan.domain.entity.Tag;
import com.mohan.domain.vo.*;
import com.mohan.mapper.ArticleMapper;
import com.mohan.mapper.ArticleTagMapper;
import com.mohan.mapper.TagMapper;
import com.mohan.service.ArticleService;
import com.mohan.service.ArticleTagService;
import com.mohan.service.CategoryService;
import com.mohan.service.TagService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.RedisCache;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private TagService tagService;
    @Override
    public ResponseResult hotArticleList() {
        // 封装查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        // 最多查10条消息
        IPage<Article> page = new Page<>(1,10);
        page(page, queryWrapper);// 分页
        List<Article> articles = page.getRecords();  // 获取查询结果

        // 查询redis实时浏览量
        replaceViewCount(articles);
        // 按照浏览量降序排序
        articles = articles.stream()
                .sorted((o1, o2) -> o2.getViewCount().intValue() - o1.getViewCount().intValue())
                .collect(Collectors.toList());

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
        articleQuery.orderByDesc(Article::getCreateTime);
        articleQuery.orderByDesc(Article::getViewCount);
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

    @Override
    @Transactional
    public ResponseResult addArticle(ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        // 添加文章
        save(article);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        // 添加标签
        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult pageList(ArticleListDto articleListDto) {
        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        // 如果存在标题
        qw.like(StringUtils.hasText(articleListDto.getTitle()),Article::getTitle,articleListDto.getTitle());
        // 如果存在摘要
        qw.like(StringUtils.hasText(articleListDto.getSummary()),Article::getSummary,articleListDto.getSummary());
        // 分页
        Page<Article> articlePage = new Page<>(articleListDto.getPageNum(), articleListDto.getPageSize());
        page(articlePage,qw);

        List<ArticlePageListVo> acs = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticlePageListVo.class);

        return ResponseResult.okResult(new PageVo(acs,articlePage.getTotal()));
    }

    @Override
    public ResponseResult getArticle(Long id) {
        Article article = getById(id);
        // 查看标签是否被删除
        Category category = categoryService.getById(article.getCategoryId());
        if (Objects.isNull(category)){
            article.setCategoryId(null);
        }
        // 根据文章id查询标签id
        LambdaQueryWrapper<ArticleTag> qw = new LambdaQueryWrapper<>();
        qw.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> list = articleTagService.list(qw);
        // 遍历标签id，过滤掉被删除的标签
        List<Long> tags = list.stream()
                .filter(articleTag -> {
                    Tag tag = tagService.getById(articleTag.getTagId());
                    return !Objects.isNull(tag);
                })
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        ArticleTagVo articleTagVo = BeanCopyUtils.copyBean(article, ArticleTagVo.class);
        articleTagVo.setTags(tags);
        return ResponseResult.okResult(articleTagVo);
    }

    @Override
    @Transactional
    public ResponseResult updateArticle(ArticleTagVo articleTagVo) {
        Article article = BeanCopyUtils.copyBean(articleTagVo, Article.class);
        updateById(article);
        List<ArticleTag> tags = articleTagVo.getTags().stream()
                .map(aLong -> new ArticleTag(articleTagVo.getId(), aLong))
                .collect(Collectors.toList());
        articleTagMapper.deleteByArticleId(article.getId());
        articleTagService.saveBatch(tags);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult delArticle(List<Long> ids) {
        removeByIds(ids);
        // 删除关联标签
        for (Long id : ids) {
            articleTagMapper.deleteByArticleId(id);
        }
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
