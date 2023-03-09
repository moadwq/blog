package com.mohan.handler.security;

import com.alibaba.fastjson.JSON;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.utils.ResponseResult;
import com.mohan.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        // 判断异常类型，响应不同信息
        ResponseResult result = null;
        if (e instanceof BadCredentialsException){
            // 用户名或密码错误等情况
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
        } else if (e instanceof InsufficientAuthenticationException) {
            // 需要登录等情况
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else {
            // 其他情况
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"认证或授权失败，请检查后重新操作");
        }
        // 响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
