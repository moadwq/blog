package com.mohan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.domain.entity.UserRole;
import com.mohan.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;
import com.mohan.service.UserRoleService;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 19:01:36
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

