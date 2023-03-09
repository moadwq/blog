package com.mohan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mohan.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-09 14:51:25
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
