package com.mohan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.domain.entity.RoleMenu;
import com.mohan.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;
import com.mohan.service.RoleMenuService;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 17:34:09
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}

