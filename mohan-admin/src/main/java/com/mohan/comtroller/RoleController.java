package com.mohan.comtroller;

import com.mohan.domain.dto.AddRoleDto;
import com.mohan.domain.dto.RoleDto;
import com.mohan.domain.dto.RoleStatusDto;
import com.mohan.domain.dto.UpdateRoleDto;
import com.mohan.domain.entity.Role;
import com.mohan.service.RoleService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
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

    @PostMapping()
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto){
        ResponseResult result = roleService.addRole(addRoleDto);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        Role role = roleService.getById(id);
        return ResponseResult.okResult(role);
    }

    @PutMapping()
    public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto){
        ResponseResult result = roleService.updateRole(updateRoleDto);
        return result;
    }

    @DeleteMapping("/{id}")
    public ResponseResult delRole(@PathVariable("id")List<Long> ids){
        ResponseResult result = roleService.delRole(ids);
        return result;
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        ResponseResult result = roleService.listAllRole();
        return result;
    }

}
