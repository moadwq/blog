package com.mohan.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListPageVo {

    private Long id;
    private String name;
    private String description;
    private String status;

}
