package com.ada.blog.service.impl;

import com.ada.blog.entity.Category;
import com.ada.blog.mapper.CategoryMapper;
import com.ada.blog.service.CategoryService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ada
 * @ClassName :CategoryServiceImpl
 * @date 2019/7/3 23:14
 * @Description:
 */
@Service("/categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 返回首页列表数据
     */
    @Override
    public PageResultUtil getBlogCategeoryPage(PageUtil pageUtil) {
        List<Category>  list = categoryMapper.findCategoryList(pageUtil);
        int total = categoryMapper.getTatolCategory(pageUtil);
        PageResultUtil pageResult = new PageResultUtil(list, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}

