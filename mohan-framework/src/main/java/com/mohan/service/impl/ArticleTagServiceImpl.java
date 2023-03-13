package com.mohan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.domain.entity.ArticleTag;
import com.mohan.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;
import com.mohan.service.ArticleTagService;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-13 20:06:54
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

