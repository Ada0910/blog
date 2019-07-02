package com.ada.blog.mapper;

import com.ada.blog.entity.Tag;
import com.ada.blog.entity.TagCount;
import com.ada.blog.util.PageUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :TagMapper
 * @date 2019/7/1 21:21
 * @Description:
 */
@Component
public interface TagMapper {

    /**
     * 标签查询并分页
     */
    List<Tag> findTagList(PageUtil pageUtil);

    /**
     * 每个标签的总数
     */
    List<TagCount> getTagCount();

    /**
     * 统计标签的总数
     */
    Integer getTotalTags(PageUtil pageUtil);

    /**
     * 根据标签id查询
     */
    Tag selectByPrimaryKey(Integer tagId);

    /**
     * 根据标签名查询
     */
    Tag selectByTagName(String tagName);

    /**
     * 根据标签id删除
     */
    Integer deleteByPrimaryKey(Integer tagId);

    /**
     * 批量插入
     */
    int insertBlogTagBatch(List<Tag> tagList);

    /**
     * 插入
     */
    int insert(Tag tag);

    /**
     * 插入，判断是否为空
     */
    int insertSelective(Tag tag);

    /**
     * 更新，有判断是否为空
     */
    int updateByPrimaryKeySelective(Tag tag);

    /**
     * 更新
     */
    int updateByPrimaryKey(Tag tag);

    /**
     * 批量删除
     */
    int deleteBatch(Integer[] ids);
}
