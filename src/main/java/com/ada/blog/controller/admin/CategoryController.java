package com.ada.blog.controller.admin;

/**
 * @author Ada
 * @ClassName :CategoryController
 * @date 2019/7/3 22:37
 * @Description:
 */

import com.ada.blog.entity.Category;
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
    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数错误!!");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultStatusUtil.successResult(categoryService.getBlogCategeoryPage(pageUtil));
    }


    /***
     * @Author Ada
     * @Date 21:36 2019/7/4
     * @Param [categoryName, categoryIcon]
     * @return com.ada.blog.util.ResultUtil
     * @Description 添加
     **/
    @RequestMapping(value = "/category/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil add(@RequestParam("categoryName") String categoryName,
                          @RequestParam("categoryIcon") String categoryIcon) {
        if (StringUtils.isEmpty(categoryName) || StringUtils.isEmpty(categoryIcon)) {
            return ResultStatusUtil.failResult("参数异常！！！");
        }
        if (categoryService.addCategory(categoryName, categoryIcon)) {
            return ResultStatusUtil.successResult();
        } else {
            return ResultStatusUtil.failResult("分类名称重复");
        }
    }


    /***
     * @Author Ada
     * @Date 22:29 2019/7/4
     * @Param [categoryName, categoryIcon, categoryId]
     * @return com.ada.blog.util.ResultUtil
     * @Description 更新
     **/
    @RequestMapping(value = "/category/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil update(@RequestParam("categoryName") String categoryName,
                             @RequestParam("categoryIcon") String categoryIcon,
                             @RequestParam("categoryId") Integer categoryId) {
        if (StringUtils.isEmpty(categoryName) || StringUtils.isEmpty(categoryIcon)) {
            return ResultStatusUtil.failResult("参数异常！！！");
        }
        if (categoryService.updateCategory(categoryId, categoryName, categoryIcon)) {
            return ResultStatusUtil.successResult();
        } else {
            return ResultStatusUtil.failResult("分类名称重复");
        }
    }

    @RequestMapping(value = "/category/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultStatusUtil.failResult("参数异常！！！");
        }
        if (categoryService.deleteBatch(ids)) {
            return ResultStatusUtil.successResult();
        } else {
            return ResultStatusUtil.failResult("删除失败");
        }
    }

    /****
     * @Author Ada
     * @Date 23:30 2019/8/23
     * @Param [id]
     * @return com.ada.blog.util.ResultUtil
     * @Description  查找分类详情
     **/
    @RequestMapping("/category/info/{id}")
    @ResponseBody
    public ResultUtil info(@PathVariable("id") Integer id) {
        Category category = categoryService.selectById(id);
        return ResultStatusUtil.successResult(category);
    }


}
