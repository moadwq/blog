package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.entity.User;
import com.mohan.mapper.UserMapper;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import com.mohan.utils.SecurityUtil;
import com.mohan.vo.UserInfoVo;
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

    @Override
    public ResponseResult getUserInfo() {
        Long userId = SecurityUtil.getUserId();
        User user = getById(userId);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }
}

