package com.mohan.comtroller;

import com.mohan.domain.dto.TagListDto;
import com.mohan.domain.entity.Tag;
import com.mohan.domain.vo.PageVo;
import com.mohan.service.TagService;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
@Api(description = "")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @ApiOperation(value = "查询标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小")
    })
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        ResponseResult<PageVo> result = tagService.pageTagList(pageNum,pageSize,tagListDto);
        return result;
    }

}
