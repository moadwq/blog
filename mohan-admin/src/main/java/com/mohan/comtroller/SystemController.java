package com.mohan.comtroller;

import com.mohan.domain.dto.UserLoginDto;
import com.mohan.domain.entity.LoginUser;
import com.mohan.domain.entity.Menu;
import com.mohan.domain.entity.User;
import com.mohan.domain.vo.AdminUserInfoVo;
import com.mohan.domain.vo.MenuVo;
import com.mohan.domain.vo.RouterVo;
import com.mohan.domain.vo.UserInfoVo;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.exception.SystemException;
import com.mohan.service.MenuService;
import com.mohan.service.RoleService;
import com.mohan.service.SystemLoginService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import com.mohan.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;
import java.util.List;


@RestController
@Api(description = "登录、权限相关接口")
public class SystemController {

    @Autowired
    private SystemLoginService systemLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;
    @PostMapping("/user/login")
    @ApiOperation(value = "用户登录")
    public ResponseResult login(@RequestBody UserLoginDto userLoginDto){
        if (!StringUtils.hasText(userLoginDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        User user = BeanCopyUtils.copyBean(userLoginDto, User.class);
        ResponseResult result = systemLoginService.login(user);
        return result;
    }

    @GetMapping("/getInfo")
    @ApiOperation(value = "查询用户的角色和权限信息")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        // 获取当前登录用户
        User user = SecurityUtil.getLoginUser().getUser();
        // 根据用户id 查询权限信息
        List<String> perms = menuService.selectPermsByUserId(user.getId());
        // 根据用户id 查询角色信息
        List<String> roles =roleService.selectRoleKeyByUserId(user.getId());
        // 封装数据返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roles, userInfoVo);

        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    @ApiOperation(value = "获得菜单信息")
    public ResponseResult<RouterVo> getRouters(){
        // 查询 menu
        ResponseResult result = menuService.selectRouterMenuTreeByUserId(SecurityUtil.getUserId());
        return result;
    }

}
