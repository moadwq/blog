package com.mohan.service.impl;

import com.mohan.contants.SystemConstants;
import com.mohan.entity.LoginUser;
import com.mohan.entity.User;
import com.mohan.service.BlogLoginService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.JwtUtil;
import com.mohan.utils.RedisCache;
import com.mohan.utils.ResponseResult;
import com.mohan.vo.BlogUserLoginVo;
import com.mohan.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override  // 登录认证
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取userId，生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId,1000*10L);
        // 把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.REDIS_USERKEY_PRE + userId,loginUser);
        // 把token和userInfo封装返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        // 获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        // 删除redis中的用户信息
        redisCache.deleteObject(SystemConstants.REDIS_USERKEY_PRE + id);
        return ResponseResult.okResult();
    }
}
