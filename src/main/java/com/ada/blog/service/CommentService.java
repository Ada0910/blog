package com.ada.blog.service;

import com.ada.blog.entity.Comment;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;

/**
 * @author Ada
 * @ClassName :CommentService
 * @date 2019/7/9 23:31
 * @Description:
 */
public interface CommentService {

    PageResultUtil getCommentPage(PageUtil pageUtil);

    Boolean reply(Long commentId, String replyBody);

    Boolean checkDone(Integer[] ids);

    Boolean deleteBatch(Integer[] ids);

    int getTotalComment();

    PageResultUtil getCommentPageByBlogIdAndPageNum(Long blogId,Integer commentPage);

    Boolean addComment(Comment comment);
}
