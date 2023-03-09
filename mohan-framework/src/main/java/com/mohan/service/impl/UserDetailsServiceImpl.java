package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mohan.entity.LoginUser;
import com.mohan.entity.User;
import com.mohan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(User::getUserName,username);
        User user = userMapper.selectOne(userQuery);
        // 判断是否查到用户，如果没查到，抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        // 返回用户信息
        // TODO 查询用户信息权限
        return new LoginUser(user);
    }
}
