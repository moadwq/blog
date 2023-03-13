package com.mohan.comtroller;

import com.mohan.domain.entity.Category;
import com.mohan.domain.vo.CategoryListVo;
import com.mohan.domain.vo.CategoryVo;
import com.mohan.service.CategoryService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/category/")
@Api(description = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @ApiOperation(value = "查询所有分类")
    public ResponseResult listAllCategory(){
        ResponseResult result = categoryService.listAllCategory();
        return result;
    }

}
