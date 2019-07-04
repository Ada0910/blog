package com.ada.blog.service.impl;

import com.ada.blog.entity.Category;
import com.ada.blog.mapper.BlogMapper;
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

    @Autowired
    private BlogMapper blogMapper;

    /**
     * 返回首页列表数据
     */
    @Override
    public PageResultUtil getBlogCategeoryPage(PageUtil pageUtil) {
        List<Category> list = categoryMapper.findCategoryList(pageUtil);
        int total = categoryMapper.getTatolCategory(pageUtil);
        PageResultUtil pageResult = new PageResultUtil(list, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    /**
     * 添加分类
     */
    @Override
    public Boolean addCategory(String categoryName, String categoryIcon) {
        Category temp = categoryMapper.selectByCategoryName(categoryName);
        if (temp == null) {
            Category category = new Category();
            category.setCategoryIcon(categoryIcon);
            category.setCategoryName(categoryName);
            return categoryMapper.insertSelective(category) > 0;
        }

        return false;
    }

    /**
     * 更新
     */
    @Override
    public Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            category.setCategoryName(categoryName);
            category.setCategoryIcon(categoryIcon);
            blogMapper.updateCategory(categoryName, category.getCategoryId(), new Integer[]{categoryId});
            return categoryMapper.updateByPrimaryKeySelective(category) > 0;
        }

        return false;
    }

    /**
     * 删除
     */
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length<1) {
            return false;
        }
        blogMapper.updateCategory("默认分类",0,ids);
        return categoryMapper.deleteBatch(ids)>0;

    }
}

