package com.mohan.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MpConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 1. 定义Mp拦截器
        MybatisPlusInterceptor mi = new MybatisPlusInterceptor();
        // 2. 添加分页拦截器
        mi.addInnerInterceptor(new PaginationInnerInterceptor());
        // 3. 添加乐观锁拦截器
//        mi.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return mi;
    }
}
