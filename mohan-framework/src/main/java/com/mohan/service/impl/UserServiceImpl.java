package com.mohan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.entity.User;
import com.mohan.mapper.UserMapper;
import org.springframework.stereotype.Service;
import com.mohan.service.UserService;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 19:16:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

