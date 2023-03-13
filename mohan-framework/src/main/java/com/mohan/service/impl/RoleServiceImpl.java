package com.mohan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.domain.entity.Role;
import com.mohan.mapper.RoleMapper;
import com.mohan.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-11 21:56:20
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 如果是超级管理员，只需要返回 "admin"
        List<String> roles = new ArrayList<>();
        if (id == 1L){
            roles.add("admin");
            return roles;
        }
        // 否则查询用户具有的角色信息
        roles = roleMapper.selectRoleKeyByUserId(id);

        return roles;
    }
}
