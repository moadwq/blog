package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.SystemConstants;
import com.mohan.domain.entity.Article;
import com.mohan.domain.entity.Category;
import com.mohan.domain.vo.CategoryListVo;
import com.mohan.mapper.CategoryMapper;
import com.mohan.service.ArticleService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import com.mohan.domain.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mohan.service.CategoryService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 14:51:30
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        // 查询文章表，状态为已发布
        LambdaQueryWrapper<Article> articleQuery = new LambdaQueryWrapper<>();
        articleQuery.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles = articleService.list(articleQuery);
        // 获取文章分类id，去重
        Set<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        // 查询分类
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                        .filter(category -> Integer.parseInt(category.getStatus()) == SystemConstants.ARTICLE_STATUS_NORMAL)
                        .collect(Collectors.toList());
        // vo去重
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        qw.eq(Category::getStatus,SystemConstants.CATEGORY_STATUS_NORMAL);
        List<Category> list = list(qw);
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(list, CategoryListVo.class);
        return ResponseResult.okResult(categoryListVos);
    }
}

