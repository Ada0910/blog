package com.ada.blog.controller.admin;

import com.ada.blog.service.BlogService;
import com.ada.blog.util.PageUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "/blog/list",method = RequestMethod.GET)
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数异常！");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultStatusUtil.successResult(blogService.getBlogPage(pageUtil));
    }

}
