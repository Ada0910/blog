package com.ada.blog.controller.admin;

import com.ada.blog.entity.AdminUser;
import com.ada.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Ada
 * @ClassName :AdminController
 * @date 2019/6/15 23:21
 * @Description: 用户登陆的controller
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;


    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }


    /**
     * @return java.lang.String
     * @Author Ada
     * @Date 17:39 2019/6/16
     * @Param [userName, password, verifyCode, session]
     * @Description 登陆
     **/
    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {

        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空！！！");
            return "admin/login";
        }

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或者密码不能为空!!!");
            return "admin/login";
        }

        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }

        AdminUser adminUser = adminUserService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            /**设置session过期时间*/
            session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "登陆失败");
            return "admin/login";

        }

    }

    /***
     * @Author Ada
     * @Date 21:39 2019/6/20
     * @Param [request]
     * @return java.lang.String
     * @Description 首页跳转请求
     **/
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        request.setAttribute("categoryCount", categoryService.getTotalCategory());
        request.setAttribute("blogCount", blogService.getTotalBlog());
        request.setAttribute("linkCount", linkService.getTotalLinks());
        request.setAttribute("tagCount", tagService.getTotalTags());
        request.setAttribute("commentCount", commentService.getTotalComment());
        request.setAttribute("path", "index");
        return "admin/index";
    }


    /**
     * @return java.lang.String
     * @Author Ada
     * @Date 23:00 2019/6/23
     * @Param [request]
     * @Description 退出
     **/
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }

    /***
     * @Author Ada
     * @Date 10:43 2019/6/24
     * @Param [request]
     * @return java.lang.String
     * @Description 用户信息修改页面
     **/
    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (Integer) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.getSession().setAttribute("loginUserName", adminUser.getLoginUserName());
        request.getSession().setAttribute("nickName", adminUser.getNickName());
        return "admin/user/profile";
    }


    /**
     * @return java.lang.String
     * @Author Ada
     * @Date 16:03 2019/6/24
     * @Param [request, originalPassword, newPassword]
     * @Description 修改密码
     **/
    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword, @RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }

        Integer loginUserId = (Integer) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    /***
     * @Author Ada
     * @Date 16:05 2019/6/24
     * @Param [request, loginUserName, nickName]
     * @return java.lang.String
     * @Description 修改登录名和昵称
     **/
    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName) {
        if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updateName(loginUserId, loginUserName, nickName)) {
            return "success";
        } else {
            return "修改失败";
        }
    }

}
