package com.mohan;

import com.mohan.mapper.CommentMapper;
import com.mohan.mapper.UserMapper;
import com.mohan.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SpringBootTest
public class MoHanBlogApplicationTests {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
    }


    @Test
    void name() {


    }
}
