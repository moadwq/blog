package com.mohan.comtroller;

import com.mohan.domain.dto.TagDto;
import com.mohan.domain.entity.Tag;
import com.mohan.domain.vo.PageVo;
import com.mohan.domain.vo.TagVo;
import com.mohan.service.TagService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

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
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagDto tagDto){
        ResponseResult<PageVo> result = tagService.pageTagList(pageNum,pageSize,tagDto);
        return result;
    }

    @PostMapping()
    @ApiOperation(value = "添加标签")
    public ResponseResult addTag(@RequestBody TagDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除标签")
    public ResponseResult delTag(@PathVariable("ids") List<Long> ids){
        tagService.removeByIds(ids);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询标签")
    @ApiImplicitParam(name = "id",value = "标签id")
    public ResponseResult getTag(@PathVariable("id") Long id){
        Tag tag = tagService.getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @PutMapping()
    @ApiOperation(value = "修改标签")
    public ResponseResult updateTag(@RequestBody TagDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }

}
