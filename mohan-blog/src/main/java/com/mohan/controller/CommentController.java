package com.mohan.controller;

import com.mohan.annotation.SystemLog;
import com.mohan.contants.SystemConstants;
import com.mohan.domain.dto.AddCommentDto;
import com.mohan.domain.entity.Comment;
import com.mohan.service.CommentService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "获得文章评论列表")
    @ApiOperation(value = "获得文章评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章id"),
            @ApiImplicitParam(name = "pageNum",value = "页码"),
            @ApiImplicitParam(name = "pageSize",value = "页面大小")
    })
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        ResponseResult result = commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
        return result;
    }

    @PostMapping
    @SystemLog(businessName = "添加评论")
    @ApiOperation(value = "添加评论")
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        ResponseResult result = commentService.addComment(comment);
        return result;
    }

    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "获得友链评论列表")
    @ApiOperation(value = "获得友链评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小")
    })
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        ResponseResult result = commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
        return result;
    }
}
