package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.SystemConstants;
import com.mohan.domain.dto.AddRoleDto;
import com.mohan.domain.dto.RoleDto;
import com.mohan.domain.dto.UpdateRoleDto;
import com.mohan.domain.entity.Role;
import com.mohan.domain.entity.RoleMenu;
import com.mohan.domain.vo.PageVo;
import com.mohan.mapper.RoleMapper;
import com.mohan.mapper.RoleMenuMapper;
import com.mohan.service.RoleMenuService;
import com.mohan.service.RoleService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
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

    @Override
    public ResponseResult pageList(RoleDto roleDto) {
        LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
        qw.like(StringUtils.hasText(roleDto.getRoleName()),Role::getRoleName,roleDto.getRoleName());
        qw.like(StringUtils.hasText(roleDto.getStatus()),Role::getStatus,roleDto.getStatus());
        qw.orderByAsc(Role::getRoleSort);
        // 分页
        Page<Role> rolePage = new Page<>(roleDto.getPageNum(),roleDto.getPageSize());
        page(rolePage,qw);

        PageVo pageVo = new PageVo(rolePage.getRecords(), rolePage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult addRole(AddRoleDto addRoleDto) {
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        save(role);
        List<RoleMenu> roleMenus = addRoleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult updateRole(UpdateRoleDto updateRoleDto) {
        // 修改角色信息
        Role role = BeanCopyUtils.copyBean(updateRoleDto, Role.class);
        updateById(role);
        // 修改菜单信息
        roleMenuMapper.deleteByRoleId(role.getId());
        List<Long> menuIds = updateRoleDto.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult delRole(List<Long> ids) {
        removeByIds(ids);
        // 删除菜单id
        for (Long id : ids) {
            roleMenuMapper.deleteByRoleId(id);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
        qw.eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL);
        List<Role> roles = list(qw);
        return ResponseResult.okResult(roles);
    }
}

