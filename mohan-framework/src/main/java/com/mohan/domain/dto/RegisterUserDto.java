package com.mohan.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户注册dto")
public class RegisterUserDto {
    //昵称
    @ApiModelProperty(notes = "昵称")
    private String nickName;
    //邮箱
    @ApiModelProperty(notes = "邮箱")
    private String email;
    //用户名
    @ApiModelProperty(notes = "用户名")
    private String userName;
    //密码
    @ApiModelProperty(notes = "密码")
    private String password;
}
