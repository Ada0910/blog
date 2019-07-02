package com.ada.blog.mapper;

import com.ada.blog.entity.TagRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :TagRelationMapper
 * @date 2019/7/2 22:47
 * @Description:
 */
@Component
public interface TagRelationMapper {

    /**
     * 根据主键查询
     */
    TagRelation selectByPrimaryKey(Long tagRelationId);

    /**
     * 根据blogId和tagId查询
     */
    TagRelation selectByBlogIdAndTagId(Long tagRelationId);

    /**
     * 根据id查询不重复的内容
     */
    List<Long> selectDistinctTagIds(Integer[] tagIds);

    /**
     * 根据主键删除
     */
    int deleteByPrimaryKey(Long tagRelationId);

    /**
     * 根据blogId删除
     */
    int deleteByBlogId(Long blogId);

    /**
     * 插入
     */
    int insert(TagRelation tagRelation);

    /**
     * 插入，判断是否为空
     */
    int insertSelective(TagRelation tagRelation);

    /**
     * 批量插入
     */
    int insertBatch(@Param("relationList") List<TagRelation> tagRelationList);

    /**
     * 更新，判断是否为空
     */
    int updateByPrimaryKeySelective(TagRelation tagRelation);

    /**
     * 更新
     */
    int updateByPrimaryKey(TagRelation tagRelation);


}
