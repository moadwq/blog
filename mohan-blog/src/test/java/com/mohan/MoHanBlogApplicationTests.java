package com.mohan;

import com.mohan.entity.Article;
import com.mohan.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MoHanBlogApplicationTests {
    @Autowired
    private ArticleService articleService;

    @Test
    void contextLoads() {
    }


    @Test
    void name() {
        List<Article> list =
                articleService.list();
        list.forEach(System.out::println);
    }
}
