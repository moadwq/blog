package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.ArticleConstants;
import com.mohan.entity.Article;
import com.mohan.mapper.ArticleMapper;
import com.mohan.service.ArticleService;
import com.mohan.utils.ResponseResult;
import com.mohan.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public ResponseResult<List<Article>> hotArticleList() {
        // 封装查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章
        queryWrapper.eq(Article::getStatus, ArticleConstants.ARTICLE_STATUS_NORMAL);
        // 按照浏览量进行降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多查10条消息
        IPage<Article> page = new Page<>(1,10);
        page(page, queryWrapper);// 分页
        List<Article> articles = page.getRecords();  // 获取查询结果

        // 将List<Article>转换为List<HotArticleVo>
        // 方式一：stream流
//        List<HotArticleVo> articleVos = articles.stream()
//                .map(HotArticleVo::new)
//                .collect(Collectors.toList());
        // 方式二：bean拷贝
        List<HotArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            HotArticleVo vo = new HotArticleVo();
            // 将article属性拷贝到vo
            BeanUtils.copyProperties(article,vo);
            articleVos.add(vo);
        }
        return ResponseResult.okResult(articles);
    }
}
