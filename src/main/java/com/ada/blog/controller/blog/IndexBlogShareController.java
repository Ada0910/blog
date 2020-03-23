package com.ada.blog.controller.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ada
 * @ClassName :IndexBlogShareController
 * @date 2020/3/23 23:39
 * @Description: 博客分享Controller
 */
@Controller
public class IndexBlogShareController {

    /**
     * 生成二维码
     */
    @RequestMapping("/blog/shareToWeChat")
    public void shareToWeChat(@RequestParam(value = "content", required = false) String content,
                              HttpServletRequest requset, HttpServletResponse response) throws Exception {

    }
}
