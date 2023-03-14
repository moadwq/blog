package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.domain.dto.UserPageDto;
import com.mohan.domain.entity.User;
import com.mohan.domain.vo.PageVo;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.exception.SystemException;
import com.mohan.mapper.UserMapper;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import com.mohan.utils.SecurityUtil;
import com.mohan.domain.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mohan.service.UserService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 19:16:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        } else if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        } else if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        } else if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        // 对数据是否重复进行判断
        if (userInfoExist(1,user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        } else if (userInfoExist(2,user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        } else if (userInfoExist(3, user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        // 密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        // 添加用户
        user.setPassword(encodePassword);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult pageList(UserPageDto userPageDto) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.like(StringUtils.hasText(userPageDto.getUserName()),User::getUserName,userPageDto.getUserName());
        qw.like(StringUtils.hasText(userPageDto.getPhonenumber()),User::getPhonenumber,userPageDto.getPhonenumber());
        qw.like(StringUtils.hasText(userPageDto.getStatus()),User::getStatus,userPageDto.getStatus());
        // 分页
        Page<User> userPage = new Page<>(userPageDto.getPageNum(), userPageDto.getPageSize());
        page(userPage,qw);

        PageVo pageVo = new PageVo(userPage.getRecords(), userPage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 判断用户信息是否存在
     * @param num  用户信息的类别
     * @param exist 用户信息
     */
    private boolean userInfoExist(Integer num,String exist) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        switch (num){
            case 1:
                queryWrapper.eq(User::getUserName,exist);
                break;
            case 2:
                queryWrapper.eq(User::getNickName,exist);
                break;
            case 3:
                queryWrapper.eq(User::getEmail,exist);
                break;
        }
        // count() 返回和条件匹配的用户数量
        return count(queryWrapper) > 0;
    }
}

