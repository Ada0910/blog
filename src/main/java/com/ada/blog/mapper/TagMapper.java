package com.ada.blog.mapper;

import com.ada.blog.entity.Tag;
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

    List<Tag> findTagList(PageUtil pageUtil);

    List<Tag> getTagCount();

    Integer getTotalTags(PageUtil pageUtil);

    Tag selectByPrimaryKey(Integer tagId);

    Tag selectByTagName(String tagName);

    Integer deleteByPrimaryKey(Integer tagId);

    int insertBlogTagBatch(List<Tag> tagList);

    int insert(Tag tag);

    int insertSelective(Tag tag);

    int updateByPrimaryKeySelective(Tag tag);

    int updateByPrimaryKey(Tag tag);


}
