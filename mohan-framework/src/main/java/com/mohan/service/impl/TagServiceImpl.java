package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.domain.dto.TagDto;
import com.mohan.domain.entity.Tag;
import com.mohan.domain.vo.PageVo;
import com.mohan.domain.vo.TagVo;
import com.mohan.mapper.TagMapper;
import com.mohan.service.TagService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-11 19:49:19
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto) {
        LambdaQueryWrapper<Tag> qw = new LambdaQueryWrapper<>();
        // 查询条件
        qw.like(StringUtils.hasText(tagDto.getName()),Tag::getName,tagDto.getName());
        qw.like(StringUtils.hasText(tagDto.getRemark()),Tag::getRemark,tagDto.getRemark());

        // 分页查询
        Page<Tag> tagPage = new Page<>(pageNum,pageSize);
        page(tagPage,qw);

        List<Tag> tags = tagPage.getRecords();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);

        PageVo pageVo = new PageVo(tagVos, tagPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }


}

