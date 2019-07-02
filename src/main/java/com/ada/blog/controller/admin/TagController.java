package com.ada.blog.controller.admin;

import com.ada.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ada
 * @ClassName :TagController
 * @date 2019/7/1 21:22
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /***
     * @Author Ada
     * @Date 23:27 2019/7/2
     * @Param [request]
     * @return java.lang.String
     * @Description 标签页面
     **/
    @RequestMapping("/tag")
    public String tagPage(HttpServletRequest request){
        request.setAttribute("path","tag");
        return "admin/tag/tag";
    }


}
