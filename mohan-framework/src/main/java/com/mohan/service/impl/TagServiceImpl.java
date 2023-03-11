package com.mohan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.domain.entity.Tag;
import com.mohan.mapper.TagMapper;
import com.mohan.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-11 19:49:19
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

