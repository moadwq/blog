package com.mohan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.mohan.mapper")
@SpringBootApplication
@EnableScheduling // 启动定时任务
public class MoHanBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoHanBlogApplication.class,args);
    }
}
