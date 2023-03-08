package com.mohan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.mohan.mapper")
@SpringBootApplication
public class MoHanBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoHanBlogApplication.class,args);
    }
}
