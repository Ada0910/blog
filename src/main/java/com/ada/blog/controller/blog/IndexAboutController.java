package com.ada.blog.controller.blog;

import com.ada.blog.entity.About;
import com.ada.blog.service.AboutService;
import com.ada.blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :AboutController
 * @date 2019/8/11 14:53
 * @Description:
 */
@Controller
public class IndexAboutController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private AboutService aboutService;

    /***
     * @Author Ada
     * @Date 21:08 2019/7/20
     * @Param [request]
     * @return java.lang.String
     * @Description 关于
     **/
    @RequestMapping(value = {"/about"}, method = RequestMethod.GET)
    public String about(HttpServletRequest request) {
        request.setAttribute("configuration", configService.getAllConfigs());
        request.setAttribute("pageName", "关于");
        Map<Byte, List<About>> list = aboutService.getAboutPage();

        if (list != null) {
            //判断关于类别并封装数据0-作者信息 1-网站版本 2-其他
            if (list.containsKey((byte) 3)) {
                request.setAttribute("otherInfos", list.get((byte) 3));
            }
            if (list.containsKey((byte) 2)) {
                request.setAttribute("webInfos", list.get((byte) 2));
            }
            if (list.containsKey((byte) 1)) {
                request.setAttribute("myInfos", list.get((byte) 1));
            }
        }
        return "blog/about/about";

    }

}
