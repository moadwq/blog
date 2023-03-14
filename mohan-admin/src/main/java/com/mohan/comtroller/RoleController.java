package com.mohan.comtroller;

import com.mohan.domain.dto.RoleDto;
import com.mohan.domain.dto.RoleStatusDto;
import com.mohan.domain.entity.Role;
import com.mohan.service.RoleService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult list(RoleDto roleDto){
        ResponseResult result = roleService.pageList(roleDto);
        return result;
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto roleStatusDto){
        roleService.updateById(new Role(roleStatusDto.getRoleId(),roleStatusDto.getStatus()));
        return ResponseResult.okResult();
    }


}
