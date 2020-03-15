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

    /**
     * 更新博客
     */
    int updateByPrimaryKeySelective(Blog tempBlog);

    /**
     * 删除博客
     */
    int deleteBatch(Integer[] ids);

    /**
     * 添加博客
     */
    int insertSelective(Blog blog);

    /**
     * 根据类型查找不同的博客
     */
    List<Blog> findBlogListByType(@Param("type") int type, @Param("limit") int limit);

    /**
     * 更新
     */
    int updateByPrimaryKey(Blog blog);

    /**
     * 根据tagId查询博客
     */
    List<Blog> getBlogPageByTagId(PageUtil pageUtil);

    /**
     * tag总数
     */
    int getTotalBlogByTagId(PageUtil pageUtil);

   Blog selectBySubUrl(String subUrl);

    List<Long> getBlogIdList();
}
