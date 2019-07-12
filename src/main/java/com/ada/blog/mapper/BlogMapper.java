package com.ada.blog.mapper;

import com.ada.blog.entity.Blog;
import com.ada.blog.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :BlogMapper
 * @date 2019/7/4 22:37
 * @Description:
 */
@Component
public interface BlogMapper {

    /**
     * 更新分类
     */
    int updateCategory(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids") Integer[] ids);

    /**
     * 返回博客管理列表
     */
    List<Blog> findBlogList(PageUtil pageUtil);

    /**
     * 返回博客的总数
     */
    int getTotalBlog(PageUtil pageUtil);

    /**
     * 根据id查找博客
     */
    Blog selectByPrimaryKey(Long blogId);
}
