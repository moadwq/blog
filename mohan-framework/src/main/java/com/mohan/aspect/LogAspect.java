package com.mohan.aspect;

import com.alibaba.fastjson.JSON;
import com.mohan.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LogAspect {

    // 切入点为标注了@SystemLog注解的方法
    @Pointcut("@annotation(com.mohan.annotation.SystemLog)")
    public void pt(){

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        try {
            Long start = handleBefore(joinPoint);

            proceed = joinPoint.proceed();

            handleAfter(proceed,start);
        } finally {
            // 结束后换行
            log.info("======================End======================" + System.lineSeparator());
        }
        return proceed;
    }


    // 被增强方法执行前
    private Long handleBefore(ProceedingJoinPoint joinPoint) {
        // 获取 request 请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        log.info("=====================Start=====================");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
        // 统计业务耗时，获得方法执行前的时间
        long start = System.currentTimeMillis();
        return start;
    }

    /**
     * 获取被增强方法上的 SystemLog 注解
     * @param joinPoint 被增强方法
     */
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        // 获取方法的标签信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法上的SystemLog注解信息
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }

    // 被增强方法执行后
    private void handleAfter(Object proceed, Long start) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(proceed));
        // 打印业务耗时
        long end = System.currentTimeMillis();
        log.info("业务耗时 ms      : {}", end-start);
    }



}
