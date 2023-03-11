package com.mohan.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    private Long id;
    //网站名称
    private String name;
    //网站log
    private String logo;
    //网站描述
    private String description;
    //网站地址
    private String address;
}
