package com.ada.blog.service;

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
}