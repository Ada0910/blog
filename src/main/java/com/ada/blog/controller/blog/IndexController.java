package com.ada.blog.controller.blog;

import com.ada.blog.entity.BlogDetail;
import com.ada.blog.entity.Comment;
import com.ada.blog.entity.Link;
import com.ada.blog.service.*;
import com.ada.blog.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :IndexController
 * @date 2019/7/17 22:32
 * @Description:网站首页
 */
@Controller
public class IndexController {

    @Autowired
    public BlogService blogService;

    @Autowired
    public TagService tagService;

    @Autowired
    public CommentService commentService;

    @Autowired
    public ConfigService configService;

    /***
     * @Author Ada
     * @Date 22:49 2019/7/17
     * @Param [request]
     * @return java.lang.String
     * @Description 网站首页
     **/
    @RequestMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        return this.page(request, 1);
    }

    /***
     * @Author Ada
     * @Date 22:53 2019/7/17
     * @Param [request, pageNum]
     * @return java.lang.String
     * @Description 首页 分页数据
     **/
    @RequestMapping({"/page/{pageNum}"})
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {

        PageResultUtil blogPageResult = blogService.getBlogForIndexPage(pageNum);
        if (blogPageResult == null) {
            return "error/error_404";
        }
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("pageName", "首页");
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configuration", configService.getAllConfigs());
        return "blog/index";
    }


    /***
     * @Author Ada
     * @Date 22:28 2019/7/19
     * @Param [request, blogId, commentPage]
     * @return java.lang.String
     * @Description 详情页
     **/
    @RequestMapping(value = {"/blog/{blogId}", "/article/{blogId}"}, method = RequestMethod.GET)
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId, @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage) {
        BlogDetail blogDetail = blogService.getBlogDetail(blogId);
        if (blogDetail != null) {
            request.setAttribute("blogDetail", blogDetail);
            request.setAttribute("commentPageResult", commentService.getCommentPageByBlogIdAndPageNum(blogId, commentPage));
        }
        request.setAttribute("pageName", "详情");
        request.setAttribute("configuration", configService.getAllConfigs());
        return "blog/content/detail";
    }

    /***
     * @Author Ada
     * @Date 23:37 2019/7/20
     * @Param [request, keyword]
     * @return java.lang.String
     * @Description
     **/
    @GetMapping({"/search/{keyword}"})
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        return search(request, keyword, 1);
    }

    /***
     * @Author Ada
     * @Date 15:39 2019/7/20
     * @Param [request, keyword, page]
     * @return java.lang.String
     * @Description 搜索列表页
     **/
    @RequestMapping(value = "/search/{keyword}/{keyword}", method = RequestMethod.GET)
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword, @PathVariable("page") Integer page) {
        //
        PageResultUtil pageResultUtil = blogService.getBlogPageBySearch(keyword, page);
        request.setAttribute("blogPageResult", pageResultUtil);
        request.setAttribute("pageName", "搜索");
        request.setAttribute("pageUrl", "search");
        request.setAttribute("keyword", keyword);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configuration", configService.getAllConfigs());
        return "blog/content/list";
    }


    /***
     * @Author Ada
     * @Date 22:59 2019/7/20
     * @Param [request, session, blogId, verifyCode, commentator, email, websiteUrl, commentBody]
     * @return com.ada.blog.util.ResultUtil
     * @Description 评论
     **/
    @PostMapping("/blog/comment")
    @ResponseBody
    public ResultUtil comment(HttpServletRequest request,
                              HttpSession session,
                              @RequestParam Long blogId, @RequestParam String verifyCode,
                              @RequestParam String commentator, @RequestParam String email,
                              @RequestParam String websiteUrl, @RequestParam String commentBody) {
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultStatusUtil.failResult("验证码不能为空");
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode)) {
            return ResultStatusUtil.failResult("非法请求");
        }
        if (!verifyCode.equals(kaptchaCode)) {
            return ResultStatusUtil.failResult("验证码错误");
        }
        String ref = request.getHeader("Referer");
        if (StringUtils.isEmpty(ref)) {
            return ResultStatusUtil.failResult("非法请求");
        }
        if (null == blogId || blogId < 0) {
            return ResultStatusUtil.failResult("非法请求");
        }
        if (StringUtils.isEmpty(commentator)) {
            return ResultStatusUtil.failResult("请输入称呼");
        }
        if (StringUtils.isEmpty(email)) {
            return ResultStatusUtil.failResult("请输入邮箱地址");
        }
        if (!PatternUtil.isEmail(email)) {
            return ResultStatusUtil.failResult("请输入正确的邮箱地址");
        }
        if (StringUtils.isEmpty(commentBody)) {
            return ResultStatusUtil.failResult("请输入评论内容");
        }
        if (commentBody.trim().length() > 200) {
            return ResultStatusUtil.failResult("评论内容过长");
        }

        Comment comment = new Comment();
        comment.setBlogId(blogId);
        comment.setCommentator(MyBlogUtil.cleanString(commentator));
        comment.setEmail(email);
        if (!PatternUtil.isURL(websiteUrl)) {
            comment.setWebsiteUrl(websiteUrl);
        }
        comment.setCommentBody(MyBlogUtil.cleanString(commentBody));
        return ResultStatusUtil.successResult(commentService.addComment(comment));
    }

    /***
     * @Author Ada
     * @Date 10:28 2019/7/21
     * @Param [request, categoryName]
     * @return java.lang.String
     * @Description 分类详情页
     **/
    @GetMapping("/category/{categoryName}")
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName) {
        return category(request, categoryName, 1);
    }

    @GetMapping("/category/{categoryName}/{page}")
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName, @PathVariable("page") int page) {
        PageResultUtil pageResultUtil = blogService.getBlogPageByCategory(categoryName, page);
        request.setAttribute("blogPageResult", pageResultUtil);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        request.setAttribute("keyword", categoryName);
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configuration", configService.getAllConfigs());
        return "blog/content/list";
    }

    /***
     * @Author Ada
     * @Date 14:40 2019/7/21
     * @Param [request, tagName]
     * @return java.lang.String
     * @Description 标签列表页
     **/
    @GetMapping({"/tag/{tagName}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName) {
        return tag(request, tagName, 1);
    }

    @GetMapping({"/tag/{tagName}/{page}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName, @PathVariable("page") Integer page) {
        PageResultUtil pageResultUtil = blogService.getBlogPageByTag(tagName, page);
        request.setAttribute("blogPageResult", pageResultUtil);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("keyword", tagName);
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configuration", configService.getAllConfigs());
        return "blog/content/list";
    }

    /***
     * @Author Ada
     * @Date 15:10 2019/7/21
     * @Param [request, subUrl]
     * @return java.lang.String
     * @Description其他的一些配置页面
     **/
    @GetMapping({"/{subUrl}"})
    public String detail(HttpServletRequest request, @PathVariable("subUrl") String subUrl) {
        BlogDetail blogDetail = blogService.getBlogDetailBySubUrl(subUrl);
        if (blogDetail != null) {
            request.setAttribute("blogDetail", blogDetail);
            request.setAttribute("pageName", subUrl);
            request.setAttribute("configuration", configService.getAllConfigs());
            return "blog/content/detail";
        } else {
            return "error/error_400";
        }
    }
}
