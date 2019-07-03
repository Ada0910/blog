package com.ada.blog.controller.admin;

import com.ada.blog.service.TagService;
import com.ada.blog.util.PageUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    public String tagPage(HttpServletRequest request) {
        request.setAttribute("path", "tag");
        return "admin/tag/tag";
    }

    /***
     * @Author Ada
     * @Date 20:36 2019/7/3
     * @Param [params]
     * @return com.ada.blog.util.ResultUtil
     * @Description 标签列表
     **/
    @RequestMapping("tag/list")
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {

        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数异常!");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultStatusUtil.successResult(tagService.getBlogTagPage(pageUtil));
    }

    /***
     * @Author Ada
     * @Date 20:40 2019/7/3
     * @Param [tagName]
     * @return com.ada.blog.util.ResultUtil
     * @Description 添加标签
     **/
    @PostMapping("/tag/add")
    @ResponseBody
    public ResultUtil add(@RequestParam("tagName") String tagName) {
        if (StringUtils.isEmpty(tagName)) {
            return ResultStatusUtil.failResult("参数异常！！");
        }
        if (tagService.saveTag(tagName)) {
            return ResultStatusUtil.successResult();
        } else {
            return ResultStatusUtil.failResult("标签名重复！！！");
        }

    }

    /***
     * @Author Ada
     * @Date 20:48 2019/7/3
     * @Param [ids]
     * @return com.ada.blog.util.ResultUtil
     * @Description 删除
     **/
    @PostMapping("/tag/delete")
    @ResponseBody
    public ResultUtil delete(@RequestBody  Integer[] ids){
        if(ids.length < 1){
            return ResultStatusUtil.failResult("参数异常!!");
        }
        if (tagService.deleteBatch(ids)) {
            return  ResultStatusUtil.successResult();
        }else{
            return ResultStatusUtil.failResult("有关联数据请勿强行删除");
        }
    }


}
