package com.ada.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ada
 * @ClassName :VersionController
 * @date 2020/2/10 22:25
 * @Description: 版本Controller
 */
@Controller
@RequestMapping("/admin")
public class VersionController {

    /***
     * @Author Ada
     * @Date 22:28 2020/2/10
     * @Param [request]
     * @return java.lang.String
     * @Description 首页跳转
     **/
    @RequestMapping("/version")
    public String aboutPage(HttpServletRequest request) {
        request.setAttribute("path", "version");
        return "admin/version/version";
    }
}
