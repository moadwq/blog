package com.mohan.vo;

import com.mohan.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;
    private String title;
    private Long viewCount;

    public HotArticleVo(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.viewCount = article.getViewCount();
    }
}
