package com.mohan.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mohan.domain.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加文章dto")
public class ArticleDto {
    //标题
    @ApiModelProperty(notes = "标题")
    private String title;
    //文章内容
    @ApiModelProperty(notes = "内容")
    private String content;
    //文章摘要
    @ApiModelProperty(notes = "摘要")
    private String summary;
    //所属分类id
    @ApiModelProperty(notes = "分类id")
    private Long categoryId;
    //缩略图
    @ApiModelProperty(notes = "缩略图")
    private String thumbnail;
    //是否置顶（0否，1是）
    @ApiModelProperty(notes = "是否置顶（0否，1是）")
    private String isTop;
    //状态（0已发布，1 草稿）
    @ApiModelProperty(notes = "状态（0已发布，1 草稿）")
    private String status;
    //是否允许评论 1是，0否
    @ApiModelProperty(notes = "是否允许评论 1是，0否")
    private String isComment;
    // 标签
    @ApiModelProperty(notes = "标签")
    private List<Tag> tags;

}
