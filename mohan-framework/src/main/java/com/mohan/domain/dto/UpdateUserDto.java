package com.mohan.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "更新用户dto")
public class UpdateUserDto {

    @ApiModelProperty(notes = "用户id")
    private Long id;
    //昵称
    @ApiModelProperty(notes = "昵称")
    private String nickName;
    //邮箱
    @ApiModelProperty(notes = "邮箱")
    private String email;
    //用户性别（0男，1女，2未知）
    @ApiModelProperty(notes = "用户性别（0男，1女，2未知）")
    private String sex;
    //头像
    @ApiModelProperty(notes = "头像")
    private String avatar;
}
