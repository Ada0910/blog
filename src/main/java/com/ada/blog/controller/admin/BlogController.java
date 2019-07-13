package com.ada.blog.controller.admin;

import com.ada.blog.entity.Blog;
import com.ada.blog.service.BlogService;
import com.ada.blog.service.CategoryService;
import com.ada.blog.util.PageUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :BlogController
 * @date 2019/7/11 22:35
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    /***
     * @Author Ada
     * @Date 22:47 2019/7/11
     * @Param [request]
     * @return java.lang.String
     * @Description 返回首页
     **/
    @RequestMapping("/blog")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "blog");
        return "admin/blog/blog";
    }

    /***
     * @Author Ada
     * @Date 23:02 2019/7/11
     * @Param [params]
     * @return com.ada.blog.util.ResultUtil
     * @Description 返回列表
     **/
    @RequestMapping(value = "/blog/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数异常！");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultStatusUtil.successResult(blogService.getBlogPage(pageUtil));
    }

    /***
     * @Author Ada
     * @Date 22:03 2019/7/12
     * @Param [request]
     * @return java.lang.String
     * @Description 发布博客
     **/
    @RequestMapping("/blog/edit")
    public String add(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        request.setAttribute("category", categoryService.getAllCategory());
        return "admin/blog/edit";
    }

    /***
     * @Author Ada
     * @Date 22:16 2019/7/12
     * @Param [request, blogId]
     * @return java.lang.String
     * @Description 修改文章
     **/
    @RequestMapping("/blog/edit/{blogId}")
    public String edit(HttpServletRequest request, @PathVariable("blogId") Long blogId) {
        request.setAttribute("path", "edit");
        Blog blog = blogService.getBlogById(blogId);
        if (blog == null) {
            return "error/error_400";
        }
        request.setAttribute("blog", blogService.getBlogById(blogId));
        request.setAttribute("category", categoryService.getAllCategory());
        return "admin/blog/edit";
    }


    /***
     * @Author Ada
     * @Date 15:34 2019/7/13
     * @Param [blogId, blogTitle, blogSubUrl, blogCategoryId, blogTags, blogContent, blogCoverImage, blogStatus, enableComment]
     * @return com.ada.blog.util.ResultUtil
     * @Description 更新博客
     **/
    @PostMapping("/blog/update")
    @ResponseBody
    public ResultUtil update(@RequestParam("blogId") Long blogId,
                             @RequestParam("blogTitle") String blogTitle,
                             @RequestParam(name = "blogSubUrl", required = false) String blogSubUrl,
                             @RequestParam("blogCategoryId") Integer blogCategoryId,
                             @RequestParam("blogTags") String blogTags,
                             @RequestParam("blogContent") String blogContent,
                             @RequestParam("blogCoverImage") String blogCoverImage,
                             @RequestParam("blogStatus") Byte blogStatus,
                             @RequestParam("enableComment") Byte enableComment) {

        if (StringUtils.isEmpty(blogTitle)) {
            return ResultStatusUtil.failResult("请输入文章标题");
        }
        if (blogTitle.trim().length() > 150) {
            return ResultStatusUtil.failResult("标题过长");
        }
        if (StringUtils.isEmpty(blogTags)) {
            return ResultStatusUtil.failResult("请输入文章标签");
        }
        if (blogTags.trim().length() > 150) {
            return ResultStatusUtil.failResult("标签过长");
        }
        if (blogSubUrl.trim().length() > 150) {
            return ResultStatusUtil.failResult("路径过长");
        }
        if (StringUtils.isEmpty(blogContent)) {
            return ResultStatusUtil.failResult("请输入文章内容");
        }
        if (blogTags.trim().length() > 1000000) {
            return ResultStatusUtil.failResult("文章内容过长");
        }
        if (StringUtils.isEmpty(blogCoverImage)) {
            return ResultStatusUtil.failResult("封面图不能为空");
        }
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setBlogTitle(blogTitle);
        blog.setBlogSubUrl(blogSubUrl);
        blog.setBlogCategoryId(blogCategoryId);
        blog.setBlogTags(blogTags);
        blog.setBlogContent(blogContent);
        blog.setBlogCoverImage(blogCoverImage);
        blog.setBlogStatus(blogStatus);
        blog.setEnableComment(enableComment);

        String updateBlog = blogService.updateBlog(blog);
        if ("success".equals(updateBlog)) {
            return ResultStatusUtil.successResult("修改成功");
        } else {
            return ResultStatusUtil.failResult(updateBlog);
        }
    }


}
