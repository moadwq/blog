package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.entity.Category;
import com.mohan.utils.ResponseResult;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 14:51:29
 */
public interface CategoryService extends IService<Category> {


    ResponseResult getCategoryList();
}
