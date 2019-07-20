package com.ada.blog.service.impl;

import com.ada.blog.entity.Comment;
import com.ada.blog.mapper.CommentMapper;
import com.ada.blog.service.CommentService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :CommentServiceImpl
 * @date 2019/7/9 23:31
 * @Description:
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 返回评论列表
     */
    @Override
    public PageResultUtil getCommentPage(PageUtil pageUtil) {
        List<Comment> comment = commentMapper.findCommentList(pageUtil);
        int total = commentMapper.getTotalComment(pageUtil);
        PageResultUtil pageResultUtil = new PageResultUtil(comment, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResultUtil;
    }

    /***
     * @Author Ada
     * @Date 22:57 2019/7/10
     * @Param [commentId, replyBody]
     * @return java.lang.Boolean
     * @Description 回复功能
     **/
    @Override
    public Boolean reply(Long commentId, String replyBody) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if (comment != null && comment.getCommentStatus().intValue() == 1) {
            comment.setReplyBody(replyBody);
            comment.setReplyCreateTime(new Date());
            return commentMapper.updateByPrimaryKeySelective(comment) > 0;
        }
        return false;
    }

    /**
     * 审核
     */
    @Override
    public Boolean checkDone(Integer[] ids) {
        return commentMapper.checkDone(ids) > 0;
    }

    /**
     * 批量删除
     */
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return commentMapper.deleteBatch(ids) > 0;
    }

    /**
     * 评论的总数
     */
    @Override
    public int getTotalComment() {
        return commentMapper.getTotalComment(null);
    }

    /**
     * 根据blogId和page查询评论
     */
    @Override
    public PageResultUtil getCommentPageByBlogIdAndPageNum(Long blogId, Integer commentPage) {
        if (commentPage < 1) {
            return null;
        }
        Map map = new HashMap();
        map.put("page", commentPage);
        map.put("limit", 8);
        map.put("blogId", blogId);
        map.put("commentStatus", 1);

        PageUtil pageUtil = new PageUtil(map);
        List<Comment> comments = commentMapper.findCommentList(pageUtil);
        if (!CollectionUtils.isEmpty(comments)) {
            int total = commentMapper.getTotalBlogComments(pageUtil);
            PageResultUtil pageResultUtil = new PageResultUtil(comments, total, pageUtil.getLimit(), pageUtil.getPage());
            return pageResultUtil;
        }
        return null;
    }

    /***
     * @Author Ada
     * @Date 23:07 2019/7/20
     * @Param [comment]
     * @return java.lang.Boolean
     * @Description 添加评论
     **/
    @Override
    public Boolean addComment(Comment comment) {
        return commentMapper.insertSelective(comment)>0;
    }


}
