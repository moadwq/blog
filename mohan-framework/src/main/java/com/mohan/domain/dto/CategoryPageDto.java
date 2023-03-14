package com.mohan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPageDto {

    private Integer pageNum;
    private Integer pageSize;
    private String name;
    private String status;

}
