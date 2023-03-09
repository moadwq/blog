package com.mohan.vo;

import com.mohan.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    private Long id;
    private String name;

}
