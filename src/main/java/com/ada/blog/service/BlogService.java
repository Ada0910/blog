package com.ada.blog.service;

import com.ada.blog.entity.Blog;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;

/**
 * @author Ada
 * @ClassName :BlogService
 * @date 2019/7/11 23:06
 * @Description:
 */
public interface BlogService {

    PageResultUtil getBlogPage(PageUtil pageUtil);

    Blog getBlogById(Long blogId);

    String updateBlog(Blog blog);

    Boolean deleteBatch(Integer[] ids);

    String addBlog(Blog blog);

}
