package com.ada.blog.controller.admin;

import com.ada.blog.service.ConfigService;
import com.ada.blog.util.ResultStatusUtil;
import com.ada.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ada
 * @ClassName :ConfigController
 * @date 2019/6/24 22:13
 * @Description:
 */

@Controller
@RequestMapping("/admin")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /***
     * @Author Ada
     * @Date 23:24 2019/6/27
     * @Param [request]
     * @return java.lang.String
     * @Description 获取所有配置的信息
     **/
    @GetMapping("/config")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "config");
        request.setAttribute("config", configService.getAllConfigs());
        return "admin/config/config";
    }

    /**
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 17:22 2019/6/29
     * @Param [websiteName, websiteDescription, websiteLogo, websiteIcon]
     * @Description 站点信息
     **/

    @PostMapping("/config/website")
    @ResponseBody
    public ResultUtil website(@RequestParam(value = "websiteName", required = false) String websiteName,
                              @RequestParam(value = "websiteDescription", required = false) String websiteDescription,
                              @RequestParam(value = "websiteLogo", required = false) String websiteLogo,
                              @RequestParam(value = "websiteIcon", required = false) String websiteIcon) {

        int updateResult = 0;
        if (!StringUtils.isEmpty(websiteName)) {
            updateResult += configService.updateConfig("websiteName", websiteName);
        }
        if (!StringUtils.isEmpty(websiteDescription)) {
            updateResult += configService.updateConfig("websiteDescription", websiteDescription);
        }
        if (!StringUtils.isEmpty(websiteLogo)) {
            updateResult += configService.updateConfig("websiteLogo", websiteLogo);
        }
        if (!StringUtils.isEmpty(websiteIcon)) {
            updateResult += configService.updateConfig("websiteIcon", websiteIcon);
        }

        return ResultStatusUtil.successResult(updateResult > 0);
    }

    /***
     * @Author Ada
     * @Date 17:59 2019/6/29
     * @Param [yourAvatar, yourName, yourEmail]
     * @return com.ada.blog.util.ResultUtil
     * @Description 个人信息更新
     **/
    @PostMapping("/config/userInfo")
    @ResponseBody
    public ResultUtil userInfo(@RequestParam(value = "yourAvatar", required = false) String yourAvatar,
                               @RequestParam(value = "yourName", required = false) String yourName,
                               @RequestParam(value = "yourEmail", required = false) String yourEmail) {
        int updateResult = 0;
        if (!StringUtils.isEmpty(yourAvatar)) {
            updateResult += configService.updateConfig("yourAvatar", yourAvatar);
        }
        if (!StringUtils.isEmpty(yourName)) {
            updateResult += configService.updateConfig("yourName", yourName);
        }
        if (!StringUtils.isEmpty(yourEmail)) {
            updateResult += configService.updateConfig("yourEmail", yourEmail);
        }
        return ResultStatusUtil.successResult(updateResult > 0);
    }

    /***
     * @Author Ada
     * @Date 17:59 2019/6/29
     * @Param [footerAbout, footerICP, footerCopyRight, footerPoweredBy, footerPoweredByURL]
     * @return com.ada.blog.util.ResultUtil
     * @Description 底部设置87
     **/
    @PostMapping("/config/footer")
    @ResponseBody
    public ResultUtil footer(@RequestParam(value = "footerAbout", required = false) String footerAbout,
                             @RequestParam(value = "footerICP", required = false) String footerICP,
                             @RequestParam(value = "footerCopyRight", required = false) String footerCopyRight,
                             @RequestParam(value = "footerPoweredBy", required = false) String footerPoweredBy,
                             @RequestParam(value = "footerPoweredByURL", required = false) String footerPoweredByURL) {
        int updateResult = 0;
        if (!StringUtils.isEmpty(footerAbout)) {
            updateResult += configService.updateConfig("footerAbout", footerAbout);
        }
        if (!StringUtils.isEmpty(footerICP)) {
            updateResult += configService.updateConfig("footerICP", footerICP);
        }
        if (!StringUtils.isEmpty(footerCopyRight)) {
            updateResult += configService.updateConfig("footerCopyRight", footerCopyRight);
        }
        if (!StringUtils.isEmpty(footerPoweredBy)) {
            updateResult += configService.updateConfig("footerPoweredBy", footerPoweredBy);
        }
        if (!StringUtils.isEmpty(footerPoweredByURL)) {
            updateResult += configService.updateConfig("footerPoweredByURL", footerPoweredByURL);
        }
        return ResultStatusUtil.successResult(updateResult > 0);
    }

}
