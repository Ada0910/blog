package com.ada.blog.service.impl;

import com.ada.blog.entity.*;
import com.ada.blog.mapper.*;
import com.ada.blog.service.BlogService;
import com.ada.blog.util.MarkDownUtil;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import com.ada.blog.util.PatternUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
        if (!CollectionUtils.isEmpty(insertTag)) {
            tagMapper.insertBlogTagBatch(insertTag);
        }

        //tag_relation表的更新
        List<TagRelation> tagRelations = new ArrayList<>();
        tagList.addAll(insertTag);
        for (Tag tag : tagList) {
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

    /***
     * @Author Ada
     * @Date 10:32 2019/7/14
     * @Param [ids]
     * @return java.lang.Boolean
     * @Description 批量删除博客
     **/
    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return blogMapper.deleteBatch(ids) > 0;
    }

    /***
     * @Author Ada
     * @Date 12:21 2019/7/14
     * @Param [blog]
     * @return java.lang.String
     * @Description 添加博客
     **/
    @Override
    @Transactional
    public String addBlog(Blog blog) {
        //Category表的更新
        Category category = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (category == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            blog.setBlogCategoryName(category.getCategoryName());
            category.setCategoryRank(category.getCategoryRank() + 1);
        }

        //处理tag（标签）表
        //取出标签名放入tags数组
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签的数量限制为6";
        }
        if (blogMapper.insertSelective(blog) > 0) {

            //更新tag表
            List<Tag> insertTag = new ArrayList<>();
            List<Tag> tagList = new ArrayList<>();
            for (int i = 0; i < tags.length; i++) {
                Tag tag = tagMapper.selectByTagName(tags[i]);
                if (tag != null) {
                    tagList.add(tag);
                } else {
                    Tag tempTag = new Tag();
                    tempTag.setTagName(tags[i]);
                    insertTag.add(tempTag);
                }
            }
            //新增标签数据并修改分类排序值
            if (!CollectionUtils.isEmpty(insertTag)) {
                tagMapper.insertBlogTagBatch(insertTag);
            }

            categoryMapper.updateByPrimaryKeySelective(category);
            //tagRelations表
            List<TagRelation> tagRelations = new ArrayList<>();
            tagList.addAll(insertTag);
            for (Tag tag : tagList) {
                TagRelation tagRelation = new TagRelation();
                tagRelation.setTagId(tag.getTagId());
                tagRelation.setBlogId(blog.getBlogId());
                tagRelations.add(tagRelation);
            }
            if (tagRelationMapper.insertBatch(tagRelations) > 0) {
                return "success";
            }
        }
        return "保存失败";
    }

    /***
     * @Author Ada
     * @Date 22:39 2019/7/15
     * @Param []
     * @return int
     * @Description 查询博客的总数
     **/
    @Override
    public int getTotalBlog() {
        return blogMapper.getTotalBlog(null);
    }

    /***
     * @Author Ada
     * @Date 23:08 2019/7/17
     * @Param [pageNum]
     * @return com.ada.blog.util.PageResultUtil
     * @Description 首页分页数据
     **/
    @Override
    public PageResultUtil getBlogForIndexPage(int page) {
        Map map = new HashMap();
        map.put("page", page);
        //每页8条
        map.put("limit", 8);
        map.put("blogStatus", 1);
        PageUtil pageUtil = new PageUtil(map);
        List<Blog> blogList = blogMapper.findBlogList(pageUtil);
        List<BlogList> blogLists = getBlogListByBlog(blogList);
        //获取博客的总数
        int total = blogMapper.getTotalBlog(pageUtil);
        //分页查询博客
        PageResultUtil pageResultUtil = new PageResultUtil(blogLists, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResultUtil;
    }

    /***
     * @Author Ada
     * @Date 22:21 2019/7/18
     * @Param [type]
     * @return java.util.List<com.ada.blog.entity.BlogList>
     * @Description 查询不同类型的博客
     **/
    @Override
    public List<BlogList> getBlogListForIndexPage(int type) {
        List<BlogList> blogLists = new ArrayList<>();
        List<Blog> blogs = blogMapper.findBlogListByType(type, 9);
        if (!CollectionUtils.isEmpty(blogs)) {
            for (Blog blog : blogs) {
                BlogList blogList = new BlogList();
                BeanUtils.copyProperties(blog, blogList);
                blogLists.add(blogList);
            }
        }
        return blogLists;
    }

    /***
     * @Author Ada
     * @Date 22:36 2019/7/19
     * @Param [blogId]
     * @return com.ada.blog.entity.BlogDetail
     * @Description 根据博客id查找博客
     **/
    @Override
    public BlogDetail getBlogDetail(Long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        //不为空且状态已发布
        BlogDetail blogDetail = getBlogDetail(blog);
        if (blogDetail != null) {
            return blogDetail;
        }
        return null;
    }

    /***
     * @Author Ada
     * @Date 16:08 2019/7/20
     * @Param [keyword, page]
     * @return com.ada.blog.util.PageResultUtil
     * @Description 搜索
     **/
    @Override
    public PageResultUtil getBlogPageBySearch(String keyword, int page) {
        if (page > 0 && PatternUtil.validKeyword(keyword)) {
            Map map = new HashMap();
            map.put("page", page);
            map.put("limit", 9);
            map.put("keyword", keyword);
            map.put("blogStatus", 1);


            PageUtil pageUtil = new PageUtil(map);
            int total = blogMapper.getTotalBlog(pageUtil);

            List<Blog> blog = blogMapper.findBlogList(pageUtil);
            List<BlogList> blogList = getBlogListByBlog(blog);

            PageResultUtil pageResultUtil = new PageResultUtil(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
            return pageResultUtil;
        }

        return null;
    }

    /***
     * @Author Ada
     * @Date 10:39 2019/7/21
     * @Param [categoryName, page]
     * @return com.ada.blog.util.PageResultUtil
     * @Description 根据分类信息查询
     **/
    @Override
    public PageResultUtil getBlogPageByCategory(String categoryName, int page) {
        if (PatternUtil.validKeyword(categoryName)) {
            Category category = categoryMapper.selectByCategoryName(categoryName);
            if ("默认分类".equals(categoryName) && category == null) {
                category = new Category();
                category.setCategoryId(0);
            }
            if (category != null && page > 0) {
                Map map = new HashMap();
                map.put("page", page);
                map.put("limit", 9);

                map.put("categoryId", category.getCategoryId());
                map.put("blogStatus", 1);
                PageUtil pageUtil = new PageUtil(map);
                List<Blog> blog = blogMapper.findBlogList(pageUtil);
                List<BlogList> blogLists = getBlogListByBlog(blog);
                int total = blogMapper.getTotalBlog(pageUtil);

                PageResultUtil pageResultUtil = new PageResultUtil(blogLists, total, pageUtil.getLimit(), pageUtil.getPage());
                return pageResultUtil;
            }
        }
        return null;
    }

    /***
     * @Author Ada
     * @Date 14:58 2019/7/21
     * @Param [tagName, page]
     * @return com.ada.blog.util.PageResultUtil
     * @Description 标签页
     **/
    @Override
    public PageResultUtil getBlogPageByTag(String tagName, int page) {
        if (PatternUtil.validKeyword(tagName)) {
            Tag tag = tagMapper.selectByTagName(tagName);
            if (tag != null && page > 0) {
                Map param = new HashMap();
                param.put("page", page);
                param.put("limit", 9);
                param.put("tagId", tag.getTagId());
                PageUtil pageUtil = new PageUtil(param);
                List<Blog> blog = blogMapper.getBlogPageByTagId(pageUtil);
                List<BlogList> blogList = getBlogListByBlog(blog);
                int total = blogMapper.getTotalBlogByTagId(pageUtil);
                PageResultUtil pageResult = new PageResultUtil(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
                return pageResult;
            }
        }

        return null;
    }

    @Override
    public BlogDetail getBlogDetailBySubUrl(String subUrl) {
        Blog blog = blogMapper.selectBySubUrl(subUrl);
        //不为空且状态为已发布
        BlogDetail blogDetail = getBlogDetail(blog);
        if (blogDetail != null) {
            return blogDetail;
        }

        return null;
    }

    /***
     * @Author Ada
     * @Date 22:52 2019/7/19
     * @Param [blog]
     * @return com.ada.blog.entity.BlogDetail
     * @Description 方法抽取
     **/
    private BlogDetail getBlogDetail(Blog blog) {
        //博客不为空且是发布状态
        if (blog != null && blog.getBlogStatus() == 1) {
            //增加浏览量
            blog.setBlogViews(blog.getBlogViews() + 1);
            blogMapper.updateByPrimaryKey(blog);

            BlogDetail blogDetail = new BlogDetail();
            BeanUtils.copyProperties(blog, blogDetail);
            blogDetail.setBlogContent(MarkDownUtil.mdToHtml(blog.getBlogContent()));


            Category category = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
            if (category == null) {
                category = new Category();
                category.setCategoryId(0);
                category.setCategoryName("默认分类");
                category.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            //分类信息
            blogDetail.setBlogCategoryIcon(category.getCategoryIcon());

            if (!StringUtils.isEmpty(blog.getBlogTags())) {
                //标签设置
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetail.setBlogTags(tags);
            }

            //设置评论数
            Map params = new HashMap();
            params.put("blogId", blog.getBlogId());
            //过滤审核通过的数据
            params.put("commentStatus", 1);
            blogDetail.setCommentCount(commentMapper.getTotalBlogComments(params));
            return blogDetail;
        }
        return null;
    }

    /**
     * @return java.util.List<com.ada.blog.entity.BlogList>
     * @Author Ada
     * @Date 21:17 2019/7/18
     * @Param [blogList]
     * @Description 获取博客的列表
     **/
    public List<BlogList> getBlogListByBlog(List<Blog> blogList) {
        List<BlogList> blogLists = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)) {
            List<Integer> categoryIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
            Map<Integer, String> blogCategoryMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(categoryIds)) {
                //根据categoryId查询category列表
                List<Category> categories = categoryMapper.selectByCategoryId(categoryIds);
                if (!CollectionUtils.isEmpty(categories)) {
                    blogCategoryMap = categories.stream().collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryIcon, (key1, key2) -> key2));
                }
            }
            for (Blog blog : blogList) {
                BlogList list = new BlogList();
                BeanUtils.copyProperties(blog, list);
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())) {
                    list.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                } else {
                    list.setBlogCategoryId(0);
                    list.setBlogCategoryName("默认分类");
                    list.setBlogCategoryIcon("/admin/dist/img/category/00.png");
                }
                blogLists.add(list);
            }
        }
        return blogLists;
    }
}
