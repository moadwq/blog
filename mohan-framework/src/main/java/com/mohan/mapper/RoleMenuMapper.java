package com.mohan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mohan.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-14 17:34:07
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
