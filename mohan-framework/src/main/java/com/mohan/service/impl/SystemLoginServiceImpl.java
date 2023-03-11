package com.mohan.service.impl;

import com.mohan.contants.SystemConstants;
import com.mohan.domain.entity.LoginUser;
import com.mohan.domain.entity.User;
import com.mohan.domain.vo.BlogUserLoginVo;
import com.mohan.domain.vo.UserInfoVo;
import com.mohan.service.BlogLoginService;
import com.mohan.service.SystemLoginService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.JwtUtil;
import com.mohan.utils.RedisCache;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SystemLoginServiceImpl implements SystemLoginService {

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
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.REDIS_ADMIN_USERKEY_PRE + userId,loginUser);
        // 把token返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

}
