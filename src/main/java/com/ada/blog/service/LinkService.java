package com.ada.blog.service;

import com.ada.blog.entity.Link;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :LinkService
 * @date 2019/6/29 23:07
 * @Description:
 */
public interface LinkService {

    /***/
    PageResultUtil getLinkPage(PageUtil pageUtil);

    /***/
    int getTotalLinks();

    /***/
    Boolean saveLink(Link link);

    /***/
    Link selectById(Integer id);

    /***/
    Boolean updateLink(Link link);

    /***/
    Boolean deleteBitch(Integer[] ids);

    /***/
    Map<Byte, List<Link>> getLinksForLinkPage();
}
