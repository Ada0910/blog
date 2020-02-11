package com.ada.blog.controller.admin;

import com.ada.blog.entity.Version;
import com.ada.blog.service.VersionService;
import com.ada.blog.util.PageUtil;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
