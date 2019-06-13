package com.ada.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ada
 * @ClassName :IndexController
 * @date 2019/6/8 23:30
 * @Description:
 */
@Controller
public class IndexController {

    @RequestMapping("/login")
    public String showInofo(Model model) {
     model.addAttribute("errorMsg","出现错误");
        return "admin/login";
    }
}
