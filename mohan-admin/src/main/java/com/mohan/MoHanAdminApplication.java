package com.mohan;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.mohan.mapper")
@SpringBootApplication
public class MoHanAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoHanAdminApplication.class,args);
    }
}
