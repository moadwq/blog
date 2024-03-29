package com.mohan.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 友链(Link)表实体类
 *
 * @author makejava
 * @since 2023-03-09 18:43:48
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mh_link")
public class Link  {
    @TableId
    private Long id;
    //网站名称
    private String name;
    //网站log
    private String logo;
    //网站描述
    private String description;
    //网站地址
    private String address;
    //审核状态 (0 代表审核通过，1 代表审核未通过，2 代表未审核)
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0 代表未删除，1 代表已删除）
    private Integer delFlag;

    public Link(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}
