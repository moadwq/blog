package com.mohan.job;

import com.mohan.contants.SystemConstants;
import com.mohan.entity.Article;
import com.mohan.mapper.ArticleMapper;
import com.mohan.service.ArticleService;
import com.mohan.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时更新文章浏览量任务
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    /**
     * 定时将redis中的文章浏览量更新到mysql中
     * 从每小时第0分开始，每十分钟执行一次定时任务
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void testJob(){
        // 查询redis中的 文章id和浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEWCOUNT_KEY);
        List<Article> articles = viewCountMap.entrySet().stream()
                .map(entry -> {
                    Article article = new Article();
                    article.setId(Long.parseLong(entry.getKey()));
                    article.setViewCount(entry.getValue().longValue());
                    return article;
                })
                .collect(Collectors.toList());
        // 更新数据库浏览量
        articleService.updateBatchById(articles);
    }
}
