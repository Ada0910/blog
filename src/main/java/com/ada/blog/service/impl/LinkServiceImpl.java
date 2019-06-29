package com.ada.blog.service.impl;

import com.ada.blog.entity.Link;
import com.ada.blog.service.LinkService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :LinkServiceImpl
 * @date 2019/6/29 23:08
 * @Description:
 */
@Service("/linkService")
public class LinkServiceImpl implements LinkService {
    @Override
    public PageResultUtil getLinkPage(PageUtil pageUtil) {
        return null;
    }

    @Override
    public int getTotalLinks() {
        return 0;
    }

    @Override
    public Boolean saveLink(Link link) {
        return null;
    }

    @Override
    public Link selectById(Integer id) {
        return null;
    }

    @Override
    public Boolean updateLink(Link link) {
        return null;
    }

    @Override
    public Boolean deleteBitch(Integer[] ids) {
        return null;
    }

    @Override
    public Map<Byte, List<Link>> getLinksForLinkPage() {
        return null;
    }
}
