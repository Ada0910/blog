package com.ada.blog.service;

import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;

/**
 * @author Ada
 * @ClassName :CategoryService
 * @date 2019/7/3 23:14
 * @Description:
 */
public interface CategoryService {

    PageResultUtil getBlogCategeoryPage(PageUtil pageUtil);

    Boolean addCategory(String categoryName, String categoryIcon);

    Boolean updateCategory(Integer categoryId,String categoryName, String categoryIcon);

    Boolean deleteBatch(Integer[] ids);
}
