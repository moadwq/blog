package com.mohan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    //菜单状态（0正常 1停用）
    private String status;
    //菜单名称
    private String menuName;

}
