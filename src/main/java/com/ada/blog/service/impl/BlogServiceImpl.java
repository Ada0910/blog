package com.ada.blog.service.impl;

import com.ada.blog.entity.Blog;
import com.ada.blog.entity.Category;
import com.ada.blog.entity.Tag;
import com.ada.blog.entity.TagRelation;
import com.ada.blog.mapper.*;
import com.ada.blog.service.BlogService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ada
 * @ClassName :BlogServiceImpl
 * @date 2019/7/11 23:06
 * @Description:
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagRelationMapper tagRelationMapper;

    @Autowired
    private CommentMapper commentMapper;

    /***
     * @Author Ada
     * @Date 23:17 2019/7/11
     * @Param [pageUtil]
     * @return com.ada.blog.util.PageResultUtil
     * @Description 返回博客列表
     **/
    @Override
    public PageResultUtil getBlogPage(PageUtil pageUtil) {
        List<Blog> list = blogMapper.findBlogList(pageUtil);
        int total = blogMapper.getTotalBlog(pageUtil);
        PageResultUtil pageResultUtil = new PageResultUtil(list, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResultUtil;
    }

    /***
     * @Author Ada
     * @Date 22:20 2019/7/12
     * @Param [blogId]
     * @return com.ada.blog.entity.Blog
     * @Description 根据id查找博客
     **/
    @Override
    public Blog getBlogById(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        return blog;
    }

    /***
     * @Author Ada
     * @Date 15:39 2019/7/13
     * @Param [blog]
     * @return java.lang.String
     * @Description 更新博客
     **/
    @Override
    public String updateBlog(Blog blog) {
        //blog表更新
        Blog tempBlog = blogMapper.selectByPrimaryKey(blog.getBlogId());
        if (tempBlog == null) {
            return "数据不存在";
        }
        tempBlog.setBlogTitle(blog.getBlogTitle());
        tempBlog.setBlogSubUrl(blog.getBlogSubUrl());
        tempBlog.setBlogContent(blog.getBlogContent());
        tempBlog.setBlogCoverImage(blog.getBlogCoverImage());
        tempBlog.setBlogStatus(blog.getBlogStatus());
        tempBlog.setEnableComment(blog.getEnableComment());

        //category表更新
        Category category = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (category == null) {
            tempBlog.setBlogCategoryId(0);
            tempBlog.setBlogCategoryName("默认分类");
        } else {
            tempBlog.setBlogCategoryName(category.getCategoryName());
            tempBlog.setBlogCategoryId(category.getCategoryId());
            category.setCategoryRank(category.getCategoryRank() + 1);
        }

        //tag表更新
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签的限制为6个";
        }
        tempBlog.setBlogTags(blog.getBlogTags());
        List<Tag> insertTag = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            Tag tag = tagMapper.selectByTagName(tags[i]);
            if (tag == null) {
                Tag tempTag = new Tag();
                tag.setTagName(tags[i]);
                insertTag.add(tempTag);
            } else {
                tagList.add(tag);
            }
        }
        //新增标签数据不为空->新增标签数据
        if(!CollectionUtils.isEmpty(insertTag)){
            tagMapper.insertBlogTagBatch(insertTag);
        }

        //tag_relation表的更新
        List<TagRelation> tagRelations = new ArrayList<>();
        tagList.addAll(insertTag);
        for(Tag tag :tagList){
            TagRelation tagRelation = new TagRelation();
            tagRelation.setBlogId(blog.getBlogId());
            tagRelation.setTagId(tag.getTagId());
            tagRelations.add(tagRelation);
        }
        //修改blog信息->修改分类排序值->删除原关系数据->保存新的关系数据
        categoryMapper.updateByPrimaryKeySelective(category);
        tagRelationMapper.deleteByBlogId(blog.getBlogId());
        tagRelationMapper.insertBatch(tagRelations);
        if (blogMapper.updateByPrimaryKeySelective(tempBlog) > 0) {
            return "success";
        }
        return "修改失败";
    }
}
