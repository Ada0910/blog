package com.ada.blog.controller.admin;

import com.ada.blog.entity.About;
import com.ada.blog.service.AboutService;
import com.ada.blog.util.PageUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :AboutController
 * @date 2019/8/10 14:53
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class AboutController {


    @Autowired
    private AboutService aboutService;

    /***
     * @Author Ada
     * @Date 14:56 2019/8/10
     * @Param [request]
     * @return java.lang.String
     * @Description 首页跳转链接
     **/
    @RequestMapping("/about")
    public String aboutPage(HttpServletRequest request) {
        request.setAttribute("path", "about");
        return "admin/about/about";
    }

    /***
     * @Author Ada
     * @Date 16:50 2019/8/10
     * @Param [params]
     * @return com.ada.blog.util.ResultUtil
     * @Description 列表
     **/
    @GetMapping("/about/list")
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数异常");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultStatusUtil.successResult(aboutService.getAboutPage(pageUtil));
    }

    /***
     * @Author Ada
     * @Date 23:01 2019/8/10
     * @Param [aboutType, aboutName, aboutUrl, aboutImage, aboutDescription]
     * @return com.ada.blog.util.ResultUtil
     * @Description 添加
     **/
    @RequestMapping("/about/add")
    @ResponseBody
    public ResultUtil add(@RequestParam("aboutType") Byte aboutType,
                          @RequestParam("aboutName") String aboutName,
                          @RequestParam("aboutUrl") String aboutUrl,
                          @RequestParam("aboutImage") String aboutImage,
                          @RequestParam("aboutDescription") String aboutDescription) {
        if (aboutType == null || aboutType < 0 || aboutImage == null || StringUtils.isEmpty(aboutImage) || StringUtils.isEmpty(aboutName) || StringUtils.isEmpty(aboutUrl) || StringUtils.isEmpty(aboutDescription)) {
            ResultStatusUtil.failResult("参数异常");
        }

        About about = new About();
        about.setAboutName(aboutName);
        about.setAboutType(aboutType);
        about.setAboutDescription(aboutDescription);
        about.setAboutUrl(aboutUrl);
        about.setAboutImage(aboutImage);
        about.setCreateTime(new Date());
        about.setUpdateTime(new Date());
        return ResultStatusUtil.successResult(aboutService.addAbout(about));
    }


    @PostMapping("/about/update")
    @ResponseBody
    public ResultUtil update(@RequestParam("aboutId") Integer aboutId,
                             @RequestParam("aboutType") Byte aboutType,
                             @RequestParam("aboutName") String aboutName,
                             @RequestParam("aboutUrl") String aboutUrl,
                             @RequestParam("aboutImage") String aboutImage,
                             @RequestParam("aboutDescription") String aboutDescription) {

        About about = aboutService.selectById(aboutId);
        if (about == null) {
            return ResultStatusUtil.failResult("无数据！");
        }
        if (aboutImage == null || StringUtils.isEmpty(aboutImage) || StringUtils.isEmpty(aboutName) || StringUtils.isEmpty(aboutUrl) || StringUtils.isEmpty(aboutDescription) || aboutType == null || aboutType < 0) {
            ResultStatusUtil.failResult("参数异常");
        }

        about.setUpdateTime(new Date());
        about.setAboutName(aboutName);
        about.setAboutType(aboutType);
        about.setAboutUrl(aboutUrl);
        about.setAboutDescription(aboutDescription);
        about.setAboutImage(aboutImage);
        return ResultStatusUtil.successResult(aboutService.updateAbout(about));
    }


    /***
     * @Author Ada
     * @Date 11:24 2019/8/11
     * @Param [id]
     * @return com.ada.blog.util.ResultUtil
     * @Description 编辑选中的数据加载到modal
     **/
    @RequestMapping("about/info/{id}")
    @ResponseBody
    public ResultUtil info(@PathVariable("id") Integer id) {
        About about = aboutService.selectById(id);
        return ResultStatusUtil.successResult(about);
    }

    @RequestMapping("/about/delete")
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultStatusUtil.failResult("参数异常");
        }

        if (aboutService.deleteBitch(ids)) {

            return ResultStatusUtil.successResult();
        } else {
            return ResultStatusUtil.failResult("删除失败");
        }
    }

}
