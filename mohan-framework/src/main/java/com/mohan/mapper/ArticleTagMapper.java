package com.mohan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mohan.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-13 20:06:52
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    void deleteByArticleId(@Param("articleId") Long articleId);

}
