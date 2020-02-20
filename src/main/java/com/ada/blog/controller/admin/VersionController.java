package com.ada.blog.controller.admin;

import com.ada.blog.entity.Version;
import com.ada.blog.service.VersionService;
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
 * @ClassName :VersionController
 * @date 2020/2/10 22:25
 * @Description: 版本Controller
 */
@Controller
@RequestMapping("/admin")
public class VersionController {

    @Autowired
    private VersionService versionService;

    /***
     * @Author Ada
     * @Date 22:28 2020/2/10
     * @Param [request]
     * @return java.lang.String
     * @Description 首页跳转
     **/
    @RequestMapping("/version")
    public String versionPage(HttpServletRequest request) {
        request.setAttribute("path", "version");
        return "admin/version/version";
    }

    /***
     * @Author Ada
     * @Date 22:21 2020/2/11
     * @Param [params]
     * @return com.ada.blog.util.ResultUtil
     * @Description 返回表格列表数据
     **/
    @GetMapping("/version/list")
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数异常");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultStatusUtil.successResult(versionService.getVersionPage(pageUtil));
    }

    /***
     * @Author Ada
     * @Date 2020/2/11
     * @Param [versionType, versionName, versionUrl, versionImage, versionDescription]
     * @return com.ada.blog.util.ResultUtil
     * @Description 版本添加
     **/
    @RequestMapping("/version/add")
    @ResponseBody
    public ResultUtil add(@RequestParam("versionId") Integer versionId,
                          @RequestParam("versionType") int versionType,
                          @RequestParam("versionSerialNum") String versionSerialNum,
                          @RequestParam("versionDescription") String versionDescription) {
        if (versionType < 0 || StringUtils.isEmpty(versionSerialNum) || StringUtils.isEmpty(versionDescription)) {
            ResultStatusUtil.failResult("参数异常");
        }

        Version version = new Version();
        version.setVersionSerialNum(versionSerialNum);
        version.setVersionDescription(versionDescription);
        version.setVersionType(versionType);
        version.setVersionCreateTime(new Date());
        version.setVersionUpdateTime(new Date());
        return ResultStatusUtil.successResult(versionService.addVersion(version));
    }

    /***
     * @Author Ada
     * @Date 21:57 2020/2/12
     * @Param [versionId, versionType, versionName, versionUrl, versionImage, versionDescription]
     * @return com.ada.blog.util.ResultUtil
     * @Description 修改
     **/
    @PostMapping("/version/update")
    @ResponseBody
    public ResultUtil update(@RequestParam("versionId") Integer versionId,
                             @RequestParam("versionType") int versionType,
                             @RequestParam("versionSerialNum") String versionSerialNum,
                             @RequestParam("versionDescription") String versionDescription) {

        Version version = versionService.selectById(versionId);
        if (version == null) {
            return ResultStatusUtil.failResult("无数据！");
        }
        if (StringUtils.isEmpty(versionSerialNum) || StringUtils.isEmpty(versionDescription) || versionType < 0) {
            ResultStatusUtil.failResult("参数异常");
        }
        version.setVersionUpdateTime(new Date());
        version.setVersionSerialNum(versionSerialNum);
        version.setVersionType(versionType);
        version.setVersionDescription(versionDescription);
        return ResultStatusUtil.successResult(versionService.updateVersion(version));
    }

    /***
     * @Author Ada
     * @Date 22:15 2020/2/12
     * @Param [id, request]
     * @return com.ada.blog.util.ResultUtil
     * @Description 修改按钮获取选中信息
     **/
    @RequestMapping("version/info/{id}")
    @ResponseBody
    public ResultUtil info(@PathVariable("id") Integer id, HttpServletRequest request) {
        Version about = versionService.selectById(id);
        request.setAttribute("about", about);
        return ResultStatusUtil.successResult(about);
    }

    /***
     * @Author Ada
     * @Date 22:19 2020/2/12
     * @Param [ids]
     * @return com.ada.blog.util.ResultUtil
     * @Description 批量删除版本信息
     **/
    @RequestMapping("/version/delete")
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultStatusUtil.failResult("参数异常");
        }

        if (versionService.deleteBatch(ids)) {

            return ResultStatusUtil.successResult();
        } else {
            return ResultStatusUtil.failResult("删除失败");
        }
    }


}
