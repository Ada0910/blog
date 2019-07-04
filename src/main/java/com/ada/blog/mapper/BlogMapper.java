package com.ada.blog.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

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

}
