package com.mohan.comtroller;

import com.mohan.annotation.SystemLog;
import com.mohan.domain.dto.UserLoginDto;
import com.mohan.domain.entity.User;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.exception.SystemException;
import com.mohan.service.SystemLoginService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(description = "后台系统相关接口")
public class SystemLoginController {

    @Autowired
    private SystemLoginService systemLoginService;

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

}
