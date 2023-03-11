package com.mohan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.mohan.mapper")
@SpringBootApplication
@EnableScheduling // 启动定时任务
@EnableSwagger2  // 启动swagger2
public class MoHanBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoHanBlogApplication.class,args);
    }
}
