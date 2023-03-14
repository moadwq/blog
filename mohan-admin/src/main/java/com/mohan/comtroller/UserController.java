package com.mohan.comtroller;

import com.mohan.domain.dto.AddUserDto;
import com.mohan.domain.dto.UpdateRoleDto;
import com.mohan.domain.dto.UpdateUserDto;
import com.mohan.domain.dto.UserPageDto;
import com.mohan.domain.entity.User;
import com.mohan.domain.vo.UserDto;
import com.mohan.domain.vo.UserRoleVo;
import com.mohan.service.UserService;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult list(UserPageDto userPageDto){
        ResponseResult result = userService.pageList(userPageDto);
        return result;
    }

    @PostMapping()
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto){
        ResponseResult result = userService.addUser(addUserDto);
        return result;
    }

    @DeleteMapping("/{id}")
    public ResponseResult delUser(@PathVariable("id") List<Long> ids){
        ResponseResult result = userService.delUser(ids);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseResult getUserAndRole(@PathVariable("id") Long id){
        ResponseResult result = userService.getUserAndRole(id);
        return result;
    }

    @PutMapping()
    public ResponseResult updateUser(@RequestBody UserDto userDto){
        ResponseResult result = userService.updateUser(userDto);
        return result;
    }

}
