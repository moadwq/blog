package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-11 21:56:20
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户id 查询角色信息
     * @param id 用户id
     */
    List<String> selectRoleKeyByUserId(Long id);
}
