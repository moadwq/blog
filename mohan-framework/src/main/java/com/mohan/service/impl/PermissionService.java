package com.mohan.service.impl;

import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.exception.SystemException;
import com.mohan.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {

    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     */
    public boolean hasPermission(String permission){
        // 如果是超级管理员，直接返回true
        if (SecurityUtil.isAdmin()){
            return true;
        }
        // 否则获取当前登陆用户所具有的的权限列表，判断是否具有该权限
        List<String> permissions = SecurityUtil.getLoginUser().getPermissions();
        if (permissions == null){
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        return permissions.contains(permission);
    }

}
