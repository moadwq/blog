package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.domain.dto.CategoryPageDto;
import com.mohan.domain.entity.Category;
import com.mohan.domain.vo.CategoryListPageVo;
import com.mohan.utils.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-09 14:51:29
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查询分类列表，若该分类没有文章，则排除
     * @return 封装了分类列表的响应对象
     */
    ResponseResult getCategoryList();

    /**
     * 查询所有状态正常的分类
     */
    ResponseResult listAllCategory();

    /**
     * 分页模糊查询所有分类
     */
    ResponseResult pageList(CategoryPageDto CategoryPageDto);

    /**
     * 导出数据到Excel
     */
    void export(HttpServletResponse response);

}
