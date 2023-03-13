package com.mohan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mohan.domain.entity.Article;
import com.mohan.domain.vo.ArticleTagVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询文章信息和标签信息
     * @param id 文章id
     */
    ArticleTagVo getArticle(@Param("id") Long id);
}
