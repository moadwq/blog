package com.mohan.comtroller;

import com.mohan.domain.dto.ArticleDto;
import com.mohan.domain.dto.ArticleListDto;
import com.mohan.service.ArticleService;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
@Api(description = "文章相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping()
    @ApiOperation(value = "添加博客")
    public ResponseResult addArticle(@RequestBody ArticleDto articleDto){
        ResponseResult result = articleService.addArticle(articleDto);
        return result;
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页查询文章列表")
    public ResponseResult list(ArticleListDto articleListDto){
        ResponseResult result = articleService.pageList(articleListDto);
        return result;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取文章详细信息")
    @ApiImplicitParam(name = "id",value = "文章id")
    public ResponseResult getArticle(@PathVariable("id") Long id){
        ResponseResult result = articleService.getArticle(id);
        return result;
    }
}
