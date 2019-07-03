package com.ada.blog.mapper;

import com.ada.blog.entity.Category;
import com.ada.blog.util.PageUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :CategoryMapper
 * @date 2019/7/3 23:21
 * @Description:
 */
@Component
public interface CategoryMapper {

    /**
     * 分页查询
     */
    List<Category> findCategoryList(PageUtil pageUtil);

    /**
     * 获取分类总数
     */
    int getTatolCategory(PageUtil pageUtil);
}
