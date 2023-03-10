package com.mohan.controller;

import com.mohan.contants.SystemConstants;
import com.mohan.entity.Comment;
import com.mohan.service.CommentService;
import com.mohan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        ResponseResult result = commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
        return result;
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        ResponseResult result = commentService.addComment(comment);
        return result;
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        ResponseResult result = commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
        return result;
    }
}
