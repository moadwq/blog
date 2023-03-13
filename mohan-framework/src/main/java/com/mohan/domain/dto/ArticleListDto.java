package com.mohan.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页查询文章列表dto")
public class ArticleListDto {
    @ApiModelProperty(notes = "页码")
    private Integer pageNum;
    @ApiModelProperty(notes = "页面大小")
    private Integer pageSize;

    //标题
    @ApiModelProperty(notes = "标题")
    private String title;

    //文章摘要
    @ApiModelProperty(notes = "摘要")
    private String summary;
}
