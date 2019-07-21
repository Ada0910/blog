package com.ada.blog.service;

import com.ada.blog.entity.Blog;
import com.ada.blog.entity.BlogDetail;
import com.ada.blog.entity.BlogList;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;

import java.util.List;

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

    int getTotalBlog();

    PageResultUtil getBlogForIndexPage(int pageNum);

    List<BlogList> getBlogListForIndexPage(int type);

    BlogDetail getBlogDetail(Long blogId);

    PageResultUtil getBlogPageBySearch(String keyword, int page);

    PageResultUtil getBlogPageByCategory(String categoryName, int page);

    PageResultUtil getBlogPageByTag(String tagName, int page);

    BlogDetail getBlogDetailBySubUrl(String subUrl);

}
