package com.mohan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mohan.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-11 19:49:17
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
