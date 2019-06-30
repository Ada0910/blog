package com.ada.blog.controller.admin;

import com.ada.blog.entity.Link;
import com.ada.blog.service.LinkService;
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
 * @ClassName :LinkController
 * @date 2019/6/29 22:21
 * @Description: 友情链接
 */
@Controller
@RequestMapping("/admin")
public class LinkController {

    @Autowired
    private LinkService linkService;


    /***
     * @Author Ada
     * @Date 10:57 2019/6/30
     * @Param [request]
     * @return java.lang.String
     * @Description 友情链接页面跳转
     **/
    @RequestMapping("/link")
    public String linkPage(HttpServletRequest request) {
        request.setAttribute("path", "link");
        return "admin/link/link";
    }

    /***
     * @Author Ada
     * @Date 11:38 2019/6/30
     * @Param [params]
     * @return com.ada.blog.util.ResultUtil
     * @Description 友情链接查询
     **/
    @GetMapping("/link/list")
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultStatusUtil.failResult("参数异常");
        }
        PageUtil pageUtil = new PageUtil(params);
        return ResultStatusUtil.successResult(linkService.getLinkPage(pageUtil));
    }

    /***
     * @Author Ada
     * @Date 16:07 2019/6/30
     * @Param [linkType, linkName, linkUrl, linkRank, linkDescription]
     * @return com.ada.blog.util.ResultUtil
     * @Description 添加友情链接
     **/
    @RequestMapping(value = "/link/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil add(@RequestParam("linkType") Integer linkType,
                          @RequestParam("linkName") String linkName,
                          @RequestParam("linkUrl") String linkUrl,
                          @RequestParam("linkRank") Integer linkRank,
                          @RequestParam("linkDescription") String linkDescription) {
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            ResultStatusUtil.failResult("参数异常");
        }

        Link link = new Link();
        link.setLinkType(linkType.byteValue());
        link.setLinkRank(linkRank);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setLinkDescription(linkDescription);
        return ResultStatusUtil.successResult(linkService.saveLink(link));
    }

    /**
     * @return com.ada.blog.util.ResultUtil
     * @Author Ada
     * @Date 16:12 2019/6/30
     * @Param [id]
     * @Description 友情链接详情
     **/
    @RequestMapping("/link/info/{id}")
    @ResponseBody
    public ResultUtil info(@PathVariable("id") Integer id) {
        Link link = linkService.selectById(id);
        return ResultStatusUtil.successResult(link);
    }

    /***
     * @Author Ada
     * @Date 16:23 2019/6/30
     * @Param [linkId, linkType, linkName, linkUrl, linkRank, linkDescription]
     * @return com.ada.blog.util.ResultUtil
     * @Description 友情链接更新
     **/
    @RequestMapping(value = "/link/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil update(@RequestParam("linkId") Integer linkId,
                             @RequestParam("linkType") Integer linkType,
                             @RequestParam("linkName") String linkName,
                             @RequestParam("linkUrl") String linkUrl,
                             @RequestParam("linkRank") Integer linkRank,
                             @RequestParam("linkDescription") String linkDescription) {

        Link updateLink = linkService.selectById(linkId);
        if (updateLink == null) {
            return ResultStatusUtil.failResult("无数据！");
        }
        if (StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription) || linkRank == null || linkRank < 0 || linkType == null || linkType < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName)) {
            return ResultStatusUtil.failResult("参数异常！");
        }

        updateLink.setLinkRank(linkRank);
        updateLink.setLinkName(linkName);
        updateLink.setLinkUrl(linkUrl);
        updateLink.setLinkDescription(linkDescription);
        updateLink.setLinkType(linkType.byteValue());
        return ResultStatusUtil.successResult(linkService.updateLink(updateLink));
    }

    /***
     * @Author Ada
     * @Date 16:27 2019/6/30
     * @Param [ids]
     * @return com.ada.blog.util.ResultUtil
     * @Description 友情链接删除
     **/
    @RequestMapping(value = "/link/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {

        if (ids.length < 1) {
            return ResultStatusUtil.failResult("参数异常");
        }
        if (linkService.deleteBitch(ids)) {
            return ResultStatusUtil.successResult();
        } else {
            return ResultStatusUtil.failResult("删除失败");
        }
    }

}
