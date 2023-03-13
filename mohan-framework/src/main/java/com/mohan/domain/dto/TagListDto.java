package com.mohan.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询标签dto")
public class TagListDto {

    @ApiModelProperty(notes = "标签名")
    private String name;
    @ApiModelProperty(notes = "备注")
    private String remark;


}
