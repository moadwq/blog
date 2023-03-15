package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.SystemConstants;
import com.mohan.domain.dto.AddUserDto;
import com.mohan.domain.dto.ChangeUserDto;
import com.mohan.domain.dto.UserPageDto;
import com.mohan.domain.entity.Role;
import com.mohan.domain.entity.User;
import com.mohan.domain.entity.UserRole;
import com.mohan.domain.vo.PageVo;
import com.mohan.domain.vo.UserDto;
import com.mohan.domain.vo.UserRoleVo;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.exception.SystemException;
import com.mohan.mapper.UserMapper;
import com.mohan.mapper.UserRoleMapper;
import com.mohan.service.RoleService;
import com.mohan.service.UserRoleService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import com.mohan.utils.SecurityUtil;
import com.mohan.domain.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mohan.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-09 19:16:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleService roleService;
    @Override
    public ResponseResult getUserInfo() {
        Long userId = SecurityUtil.getUserId();
        User user = getById(userId);

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        } else if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        } else if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        } else if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        // 对数据是否重复进行判断
        if (userInfoExist(1,user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        } else if (userInfoExist(2,user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
//        else if (userInfoExist(3, user.getEmail())) {
//            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
//        }
        // 密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        // 添加用户
        user.setPassword(encodePassword);
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult pageList(UserPageDto userPageDto) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.like(StringUtils.hasText(userPageDto.getUserName()),User::getUserName,userPageDto.getUserName());
        qw.like(StringUtils.hasText(userPageDto.getPhonenumber()),User::getPhonenumber,userPageDto.getPhonenumber());
        qw.like(StringUtils.hasText(userPageDto.getStatus()),User::getStatus,userPageDto.getStatus());
        // 分页
        Page<User> userPage = new Page<>(userPageDto.getPageNum(), userPageDto.getPageSize());
        page(userPage,qw);

        PageVo pageVo = new PageVo(userPage.getRecords(), userPage.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addUser(AddUserDto addUserDto) {
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        // 对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        } else if (!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        } else if (!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        } else if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        // 对数据是否重复进行判断
        if (userInfoExist(1,user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        } else if (userInfoExist(2,user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
//        else if (userInfoExist(3, user.getEmail())) {
//            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
//        } else if (userInfoExist(4, user.getPhonenumber())) {
//            throw new SystemException(AppHttpCodeEnum.PHONE_NUMBER_EXIST);
//        }
        // 密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        // 添加用户
        save(user);
        // 添加用户关联的角色id
        List<UserRole> userRoles = addUserDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult delUser(List<Long> ids) {
        removeByIds(ids);
        // 删除关联的角色id
        for (Long id : ids) {
            userRoleMapper.deleteByUserId(id);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserAndRole(Long id) {
        User user = getById(id);
        // 查用户具有的角色id
        LambdaQueryWrapper<UserRole> urq = new LambdaQueryWrapper<>();
        urq.eq(UserRole::getUserId,user.getId());
        List<UserRole> userRoles = userRoleService.list(urq);
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        // 查所有角色
        LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
        qw.eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL);
        List<Role> roles = roleService.list(qw);

        UserRoleVo userRoleVo = new UserRoleVo(roleIds, roles, user);
        return ResponseResult.okResult(userRoleVo);
    }

    @Override
    @Transactional
    public ResponseResult updateUser(UserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        // 修改用户信息
        updateById(user);
        // 修改角色id
        userRoleMapper.deleteByUserId(user.getId());
        List<Long> roleIds = userDto.getRoleIds();
        List<UserRole> userRoles = roleIds.stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(ChangeUserDto cd) {
        User user = new User(cd.getUserId(), cd.getStatus());
        updateById(user);
        return ResponseResult.okResult();
    }

    /**
     * 判断用户信息是否存在
     * @param num  用户信息的类别
     * @param exist 用户信息
     */
    private boolean userInfoExist(Integer num,String exist) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        switch (num){
            case 1:
                queryWrapper.eq(User::getUserName,exist);
                break;
            case 2:
                queryWrapper.eq(User::getNickName,exist);
                break;
            case 3:
                queryWrapper.eq(User::getEmail,exist);
                break;
            case 4:
                queryWrapper.eq(User::getPhonenumber,exist);
                break;
        }
        // count() 返回和条件匹配的用户数量
        return count(queryWrapper) > 0;
    }
}

