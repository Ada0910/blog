package com.ada.blog.controller.admin;

import com.ada.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String list(HttpServletRequest request){
        request.setAttribute("path","config");
        request.setAttribute("config",configService.getAllConfigs());
        return "admin/config/config";
    }






}
