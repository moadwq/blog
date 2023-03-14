package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.SystemConstants;
import com.mohan.domain.dto.MenuDto;
import com.mohan.domain.entity.Menu;
import com.mohan.domain.entity.RoleMenu;
import com.mohan.domain.vo.*;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.exception.SystemException;
import com.mohan.mapper.MenuMapper;
import com.mohan.service.MenuService;
import com.mohan.service.RoleMenuService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-11 21:50:11
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        List<String> perms;
        // 如果是超级管理员，返回所有的权限
        if (id == 1L){
            LambdaQueryWrapper<Menu> qw = new LambdaQueryWrapper<>();
            // 查询权限类型为 "C"、"F"，状态为正常的权限
            qw.in(Menu::getMenuType, SystemConstants.MENUTYPE_MENU,SystemConstants.MENUTYPE_BUTTON);
            qw.eq(Menu::getStatus,SystemConstants.MENU_STATUS_NORMAL);
            List<Menu> menus = list(qw);
            perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        // 否则返回具体的权限
        perms = menuMapper.selectPermsByUserId(id);

        return perms;
    }

    @Override
    public ResponseResult selectRouterMenuTreeByUserId(Long userId) {
        List<MenuVo> menuVos = null;
        // 如果是管理员，返回所有菜单
        if(userId == 1L){
            menuVos = menuMapper.selectAllRouterMenu();
        }else {
            // 否则返回具体的菜单
            menuVos = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建父子菜单
        List<MenuVo> menuTree = builderMenuTree(menuVos,0L);

        return ResponseResult.okResult(new RouterVo(menuTree));
    }

    @Override
    public ResponseResult likeList(MenuDto menuDto) {
        LambdaQueryWrapper<Menu> mq = new LambdaQueryWrapper<>();
        mq.like(StringUtils.hasText(menuDto.getMenuName()),Menu::getMenuName,menuDto.getMenuName());
        mq.like(StringUtils.hasText(menuDto.getStatus()),Menu::getStatus,menuDto.getStatus());
        mq.orderByAsc(Menu::getParentId);
        mq.orderByAsc(Menu::getOrderNum);
        List<Menu> list = list(mq);
        List<MenuListVo> menuListVos = BeanCopyUtils.copyBeanList(list, MenuListVo.class);

        return ResponseResult.okResult(menuListVos);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        // 上级菜单 父id，不能是自己
        if (menu.getParentId() == menu.getId() || menu.getParentId().equals(menu.getId())){
            throw new SystemException(AppHttpCodeEnum.MENU_FAILED);
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delMenu(Long id) {
        // 如果有子菜单，不能删除
        LambdaQueryWrapper<Menu> qw = new LambdaQueryWrapper<>();
        qw.eq(Menu::getParentId,id);
        List<Menu> list = list(qw);
        System.err.println(list);
        if(list.size() != 0){
            throw new SystemException(AppHttpCodeEnum.HAVE_CHILDREN_MENU);
        }
        // 没有子菜单，可以删除
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<MenuTreeVo> treeselect() {
        LambdaQueryWrapper<Menu> qw = new LambdaQueryWrapper<>();
        qw.orderByAsc(Menu::getParentId);
        qw.orderByAsc(Menu::getOrderNum);
        List<Menu> list = list(qw);
        List<MenuTreeVo> menuTreeVos = list.stream()
                .map(menu -> new MenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(),null))
                .collect(Collectors.toList());
        // 迭代构建属性父子菜单
        List<MenuTreeVo> treeVos = builderMenuTree2(menuTreeVos, 0L);
        return treeVos;

    }

    @Override
    public ResponseResult roleMenuTreeSelect(Long id) {
        // 查询所有菜单，树形展示
        List<MenuTreeVo> treeselect = treeselect();
        // 查询角色拥有的菜单id
        LambdaQueryWrapper<RoleMenu> qw = new LambdaQueryWrapper<>();
        qw.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> list = roleMenuService.list(qw);
        List<Long> menuIds = list.stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
        RoleMenuVo roleMenuVo = new RoleMenuVo(treeselect, menuIds);
        return ResponseResult.okResult(roleMenuVo);
    }

    /**
     * 构建父子菜单,方法递归
     * @param menuVos 所有菜单的集合
     * @param parentId 父id
     */
    private List<MenuVo> builderMenuTree(List<MenuVo> menuVos, Long parentId) {
        List<MenuVo> menuTree = menuVos.stream()
                // 过滤掉父id不符合的元素
                .filter(menuVo -> menuVo.getParentId().equals(parentId))
                // 根据父菜单id = 子菜单父id，查找父菜单的子菜单，并设置到父菜单的children属性中
                .map(menuVo -> menuVo.setChildren(builderMenuTree(menuVos, menuVo.getId())))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 构建父子菜单,方法递归
     * @param menuTreeVos 所有菜单的集合
     * @param parentId 父id
     */
    private List<MenuTreeVo> builderMenuTree2(List<MenuTreeVo> menuTreeVos, Long parentId) {
        List<MenuTreeVo> treeVos = menuTreeVos.stream()
                // 过滤掉父id不符合的元素
                .filter(menuTreeVo -> menuTreeVo.getParentId().equals(parentId))
                // 根据父菜单id = 子菜单父id，查找父菜单的子菜单，并设置到父菜单的children属性中
                .map(menuTreeVo -> menuTreeVo.setChildren(builderMenuTree2(menuTreeVos, menuTreeVo.getId())))
                .collect(Collectors.toList());
        return treeVos;
    }

}

