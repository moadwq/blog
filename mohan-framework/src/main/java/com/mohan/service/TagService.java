package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.domain.dto.TagDto;
import com.mohan.domain.entity.Tag;
import com.mohan.domain.vo.PageVo;
import com.mohan.utils.ResponseResult;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-11 19:49:18
 */
public interface TagService extends IService<Tag> {

    /**
     * 分页查询标签
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @param tagListDto 查询条件
     * @return
     */
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagDto tagListDto);
}
