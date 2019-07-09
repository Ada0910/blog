package com.ada.blog.service.impl;

import com.ada.blog.mapper.CommentMapper;
import com.ada.blog.service.CommentService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
