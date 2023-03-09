package com.mohan.vo;

import com.mohan.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo implements Serializable {
    private Long id;
    private String name;

    public CategoryVo(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
