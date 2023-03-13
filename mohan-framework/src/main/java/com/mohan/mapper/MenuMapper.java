package com.mohan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mohan.domain.entity.Menu;
import com.mohan.domain.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-11 21:50:09
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id查询用户权限
     * @param userId 用户id
     */
    List<String> selectPermsByUserId(@Param("userId") Long userId);

    /**
     * 查询所有菜单
     */
    List<MenuVo> selectAllRouterMenu();

    /**
     * 根据id查询菜单
     */
    List<MenuVo> selectRouterMenuTreeByUserId(@Param("userId") Long userId);



}
