package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.domain.dto.AddUserDto;
import com.mohan.domain.dto.UserPageDto;
import com.mohan.domain.entity.User;
import com.mohan.utils.ResponseResult;

import java.util.List;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 19:16:29
 */
public interface UserService extends IService<User> {

    /**
     * 获取用户信息
     */
    ResponseResult getUserInfo();

    /**
     * 修改用户信息
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 用户注册
     */
    ResponseResult register(User user);

    /**
     * 分页模糊查询
     */
    ResponseResult pageList(UserPageDto userPageDto);

    /**
     * 添加用户和其关联的角色id
     */
    ResponseResult addUser(AddUserDto addUserDto);

    /**
     * 删除用户和其关联的角色id
     */
    ResponseResult delUser(List<Long> ids);
}
