package com.ada.blog.controller.blog;

import com.ada.blog.entity.Link;
import com.ada.blog.service.ConfigService;
import com.ada.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :LinkController
 * @date 2019/8/11 14:50
 * @Description:
 */
@Controller
public class IndexLinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private ConfigService configService;

    /***
     * @Author Ada
     * @Date 20:21 2019/7/20
     * @Param [request]
     * @return java.lang.String
     * @Description 友情链接
     **/
    @RequestMapping(value = {"/link"}, method = RequestMethod.GET)
    public String link(HttpServletRequest request) {
        request.setAttribute("pageName", "友情链接");
        Map<Byte, List<Link>> listMap = linkService.getLinksForLinkPage();
        if (listMap != null) {
            //判断友链类别并封装数据0-友链 1-社区 2-网站
            if (listMap.containsKey((byte) 0)) {
                request.setAttribute("favoriteLinks", listMap.get((byte) 0));
            }
            if (listMap.containsKey((byte) 1)) {
                request.setAttribute("recommendLinks", listMap.get((byte) 1));
            }
            if (listMap.containsKey((byte) 2)) {
                request.setAttribute("webLinks", listMap.get((byte) 2));
            }
        }
        request.setAttribute("configuration", configService.getAllConfigs());
        return "blog/link/link";
    }

}

