package com.ada.blog.service;

import com.ada.blog.entity.TagCount;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;

import java.util.List;

/**
 * @author Ada
 * @ClassName :TagService
 * @date 2019/7/1 21:19
 * @Description:
 */
public interface TagService {

    PageResultUtil getBlogTagPage(PageUtil pageUtil);

    int getTotalTags();

    Boolean saveTag(String tagName);

    Boolean deleteBatch(Integer[] ids);

    List<TagCount> getBlogTagCountForIndex();
}
