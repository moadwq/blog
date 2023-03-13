package com.mohan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mohan.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-11 21:56:18
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询用户角色
     * @param userId 用户id
     */
    List<String> selectRoleKeyByUserId(@Param("userId") Long userId);
}
