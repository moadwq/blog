package com.mohan.runner;

import com.mohan.contants.SystemConstants;
import com.mohan.entity.Article;
import com.mohan.mapper.ArticleMapper;
import com.mohan.service.ArticleService;
import com.mohan.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 程序初始化时，将文章浏览量存入redis中
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        // 查询博客信息 id 和 浏览量
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> map = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        // 存入redis
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEWCOUNT_KEY,map);
    }
}
