package com.ada.blog.controller.blog;

import com.ada.blog.service.*;
import com.ada.blog.util.PageResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
    public LinkService linkService;

    @Autowired
    public CommentService commentServic;

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
        request.setAttribute("pageName","首页");
        request.setAttribute("configuration",configService.getAllConfigs());
        return "blog/index";
    }

}
