package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.domain.dto.AddRoleDto;
import com.mohan.domain.dto.RoleDto;
import com.mohan.domain.dto.UpdateRoleDto;
import com.mohan.domain.entity.Role;
import com.mohan.utils.ResponseResult;

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

    /**
     * 分页模糊查询角色
     */
    ResponseResult pageList(RoleDto roleDto);

    /**
     * 添加角色
     */
    ResponseResult addRole(AddRoleDto addRoleDto);

    /**
     * 更新角色信息和其拥有的菜单信息
     */
    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    /**
     * 删除角色和其关联的菜单
     */
    ResponseResult delRole(List<Long> ids);

    /**
     * 查询所有状态正常的角色
     * @return
     */
    ResponseResult listAllRole();

}
