package com.ada.blog.controller.admin;

/**
 * @author Ada
 * @ClassName :CategoryController
 * @date 2019/7/3 22:37
 * @Description:
 */

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

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /***
     * @Author Ada
     * @Date 22:40 2019/7/3
     * @Param [request]
     * @return java.lang.String
     * @Description 首页跳转
     **/
    @GetMapping("/category")
    public String category(HttpServletRequest request) {
        request.setAttribute("path", "category");
        return "admin/category/category";
    }

    /***
     * @Author Ada
     * @Date 23:18 2019/7/3
     * @Param [params]
     * @return com.ada.blog.util.ResultUtil
     * @Description 列表
     **/
    @RequestMapping(value = "/category/list",method = RequestMethod.GET)
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String,Object> params){
        if(StringUtils.isEmpty(params.get("page"))||StringUtils.isEmpty(params.get("limit"))){
            return ResultStatusUtil.failResult("参数错误!!");
        }
        PageUtil pageUtil= new PageUtil(params);
        return  ResultStatusUtil.successResult(categoryService.getBlogCategeoryPage(pageUtil));
    }
}
