package com.mohan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mohan.entity.Comment;
import com.mohan.utils.ResponseResult;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-10 00:53:44
 */
public interface CommentService extends IService<Comment> {

    /**
     * 获取评论列表
     *
     * @param commentType 评论类型
     * @param articleId 文章id
     * @param pageNum   页码
     * @param pageSize  页面大小
     */
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 添加一条评论
     */
    ResponseResult addComment(Comment comment);
}
