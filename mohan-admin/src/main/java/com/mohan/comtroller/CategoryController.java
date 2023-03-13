package com.mohan.comtroller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.mohan.domain.entity.Category;
import com.mohan.domain.vo.CategoryListPageVo;
import com.mohan.domain.vo.CategoryListVo;
import com.mohan.domain.vo.CategoryVo;
import com.mohan.domain.vo.ExcelCategoryVo;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.service.CategoryService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import com.mohan.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
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

    @GetMapping("/list")
    @ApiOperation(value = "分页查询所有分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页码"),
            @ApiImplicitParam(name = "pageSize",value = "页面大小")
    })
    public ResponseResult pageList(Integer pageNum,Integer pageSize){
        ResponseResult result = categoryService.pageList(pageNum,pageSize);
        return result;
    }


    @GetMapping("/export")
    @ApiOperation(value = "导出数据到Excel")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    public void export(HttpServletResponse response){
        categoryService.export(response);
    }


}
