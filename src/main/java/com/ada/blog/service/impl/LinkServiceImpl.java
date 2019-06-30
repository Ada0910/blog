package com.ada.blog.service.impl;

import com.ada.blog.entity.Link;
import com.ada.blog.mapper.LinkMapper;
import com.ada.blog.service.LinkService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Ada
 * @ClassName :LinkServiceImpl
 * @date 2019/6/29 23:08
 * @Description:
 */
@Service("/linkService")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    /**
     * 查询友情链接的结果（分页形式返回）
     */
    @Override
    public PageResultUtil getLinkPage(PageUtil pageUtil) {
        List<Link> links = linkMapper.findLinkList(pageUtil);
        int total = linkMapper.getTotalLinks(pageUtil);
        PageResultUtil pageResultUtil = new PageResultUtil(links, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResultUtil;
    }

    /**
     * 返回查询的总数
     */
    @Override
    public Integer getTotalLinks() {
        return linkMapper.getTotalLinks(null);
    }


    /**
     * 插入友情链接
     */
    @Override
    public Boolean saveLink(Link link) {
        return linkMapper.insertSelective(link) > 0;
    }

    /**
     * 根据id查询对应的友情链接
     */
    @Override
    public Link selectById(Integer id) {
        return linkMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新（判断空格）
     */
    @Override
    public Boolean updateLink(Link link) {
        return linkMapper.updateByPrimaryKey(link) > 0;
    }

    /**
     * 批量删除
     */
    @Override
    public Boolean deleteBitch(Integer[] ids) {
        return linkMapper.deleteBatch(ids) > 0;
    }

    /**
     * 根据类型分组
     */
    @Override
    public Map<Byte, List<Link>> getLinksForLinkPage() {
        List<Link> links = linkMapper.findLinkList(null);
        if (!CollectionUtils.isEmpty(links)) {
            Map<Byte, List<Link>> listMap = links.stream().collect(Collectors.groupingBy(Link::getLinkType));
            return listMap;
        }

        return null;
    }
}
