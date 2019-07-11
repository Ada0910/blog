package com.ada.blog.mapper;

import com.ada.blog.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :CommentMapper
 * @date 2019/7/9 23:45
 * @Description:
 */
@Component
public interface CommentMapper {

    /**
     * 查询评论列表
     */
    List<Comment> findCommentList(Map map);

    /**
     * 评论的总条数
     */
    int getTotalComment(Map map);

    /**
     * 根据id查询评论内容
     */
    Comment selectByPrimaryKey(Long commentId);

    /**
     * 更新
     */
    int updateByPrimaryKeySelective(Comment comment);

    /**
     * 审核
     */
    int checkDone(Integer[] ids);

    /**
     * 批量删除
     */
    int deleteBatch(Integer[] ids);
}
