package com.mohan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mohan.contants.SystemConstants;
import com.mohan.entity.Comment;
import com.mohan.enums.AppHttpCodeEnum;
import com.mohan.exception.SystemException;
import com.mohan.mapper.CommentMapper;
import com.mohan.service.CommentService;
import com.mohan.service.UserService;
import com.mohan.utils.BeanCopyUtils;
import com.mohan.utils.ResponseResult;
import com.mohan.vo.CommentVo;
import com.mohan.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-10 00:53:44
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章根评论
        LambdaQueryWrapper<Comment> commentQuery = new LambdaQueryWrapper<>();
        // 判断评论类型，文章评论时需要 根据文章id查
        commentQuery.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        // 根评论id为-1
        commentQuery.eq(Comment::getRootId,-1);
        // 评论类型
        commentQuery.eq(Comment::getType,commentType);
        // 时间升序
        commentQuery.orderByAsc(Comment::getCreateTime);
        // 分页查询
        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,commentQuery);
        
        List<CommentVo> commentVos = toCommentVoList(page.getRecords());

        commentVos.stream()
                .forEach(commentVo -> {
                    // 根据父评论id查询子评论
                    List<CommentVo> children = getChildren(commentVo.getId());
                    commentVo.setChildren(children);
                });

        
        return ResponseResult.okResult(new PageVo(commentVos,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据评论id查询对应的子评论集合
     * @param id 父评论id
     * @return 子评论集合
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> commentQuery = new LambdaQueryWrapper<>();
        // 根据父评论id查询子评论
        commentQuery.eq(Comment::getRootId,id);
        // 按照创建时间排序
        commentQuery.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(commentQuery);
        // 类型转换并赋昵称
        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    /**
     * 将List<Comment>转换为List<CommentVo>，并给每个元素赋昵称和回复对象昵称
     */
    private List<CommentVo> toCommentVoList(List<Comment> comments){
        // 转换为Vo类型
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        commentVos.stream()
                .forEach(commentVo -> {
                    // 通过创建者id查询昵称
                    String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
                    commentVo.setUsername(nickName);
                    // 如果 toCommentId 不为 -1 ，查询 toCommentUsername
                    if (commentVo.getToCommentId() != -1){
                        String toCommentUsername = userService.getById(commentVo.getToCommentUserId()).getNickName();
                        commentVo.setToCommentUserName(toCommentUsername);
                    }
                });
        return commentVos;
    }
}

