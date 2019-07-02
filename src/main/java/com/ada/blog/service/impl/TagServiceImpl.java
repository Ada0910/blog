package com.ada.blog.service.impl;

import com.ada.blog.entity.Tag;
import com.ada.blog.entity.TagCount;
import com.ada.blog.mapper.TagMapper;
import com.ada.blog.mapper.TagRelationMapper;
import com.ada.blog.service.TagService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ada
 * @ClassName :TagServiceImpl
 * @date 2019/7/1 21:20
 * @Description:
 */
@Service("/tagService")
public class TagServiceImpl implements TagService {


    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagRelationMapper tagRelationMapper;

    /**
     * 查询
     */
    @Override
    public PageResultUtil getBlogTagPage(PageUtil pageUtil) {
        List<Tag> tag = tagMapper.findTagList(pageUtil);
        int total = tagMapper.getTotalTags(pageUtil);
        PageResultUtil pageResultUtil = new PageResultUtil(tag, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResultUtil;
    }

    /**
     * 查询总数
     */
    @Override
    public int getTotalTags() {
        return tagMapper.getTotalTags(null);
    }

    /**
     * 添加标签
     */
    @Override
    public Boolean saveTag(String tagName) {
        Tag temp = tagMapper.selectByTagName(tagName);
        if (temp == null) {
            Tag tag = new Tag();
            tag.setTagName(tagName);
            return tagMapper.insertSelective(tag) > 0;
        }
        return false;
    }

    /**
     * 批量删除
     */
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        List<Long> relations = tagRelationMapper.selectDistinctTagIds(ids);
        if (!CollectionUtils.isEmpty(relations)) {
            return false;
        }

        return tagMapper.deleteBatch(ids) > 0;
    }

    /**
     * 返回每个标签的文章总数
     */
    @Override
    public List<TagCount> getBlogTagCountForIndex() {
        return tagMapper.getTagCount();
    }
}

