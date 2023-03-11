package com.mohan.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加评论dto")
public class AddCommentDto {
    //文章id
    @ApiModelProperty(notes = "文章id")
    private Long articleId;
    //评论类型（0代表文章评论，1代表友链评论）
    @ApiModelProperty(notes = "评论类型（0代表文章评论，1代表友链评论）")
    private String type;
    //根评论id
    @ApiModelProperty(notes = "根评论id")
    private Long rootId;
    //回复目标评论id
    @ApiModelProperty(notes = "所回复的目标评论的id")
    private Long toCommentId;
    //所回复的目标评论的userid
    @ApiModelProperty(notes = "所回复的目标评论的用户id")
    private Long toCommentUserId;
    //评论内容
    @ApiModelProperty(notes = "评论内容")
    private String content;
}
