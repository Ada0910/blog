package com.ada.blog.service.impl;

import com.ada.blog.entity.Comment;
import com.ada.blog.mapper.CommentMapper;
import com.ada.blog.service.CommentService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        PageResultUtil pageResultUtil = new PageResultUtil(comment, total, pageUtil.getLimit(),pageUtil.getPage());
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
      if(comment != null && comment.getCommentStatus().intValue() == 1){
          comment.setReplyBody(replyBody);
          comment.setReplyCreateTime(new Date());
          return commentMapper.updateByPrimaryKeySelective(comment)>0;
      }
        return false;
    }
}
