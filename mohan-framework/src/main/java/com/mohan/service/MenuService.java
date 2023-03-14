package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.domain.dto.MenuDto;
import com.mohan.domain.entity.Menu;
import com.mohan.domain.vo.RouterVo;
import com.mohan.utils.ResponseResult;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-11 21:50:11
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户id 查询权限信息
     * @param userId 用户id
     */
    List<String> selectPermsByUserId(Long userId);


    /**
     * 查询父子菜单
     */
    ResponseResult selectRouterMenuTreeByUserId(Long userId);


    /**
     * 模糊查询所有菜单
     */
    ResponseResult likeList(MenuDto menuDto);

    /**
     * 修改菜单信息
     */
    ResponseResult updateMenu(Menu menu);

    /**
     * 删除菜单
     */
    ResponseResult delMenu(Long id);
}
