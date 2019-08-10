package com.ada.blog.service.impl;

import com.ada.blog.entity.About;
import com.ada.blog.mapper.AboutMapper;
import com.ada.blog.service.AboutService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ada
 * @ClassName :AboutServiceImpl
 * @date 2019/8/10 16:02
 * @Description:
 */
@Service("/aboutService")
public class AboutServiceImpl implements AboutService {

    @Autowired
    private AboutMapper aboutMapper;

    /***
     * @Author Ada
     * @Date 19:18 2019/8/10
     * @Param [pageUtil]
     * @return com.ada.blog.util.PageResultUtil
     * @Description 获取列表
     **/
    @Override
    public PageResultUtil getAboutPage(PageUtil pageUtil) {

        List<About> about = aboutMapper.getAboutList(pageUtil);
        int total = aboutMapper.getTotalAbouts(pageUtil);
        PageResultUtil pageResultUtil = new PageResultUtil(about, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResultUtil;
    }

    /***
     * @Author Ada
     * @Date 23:03 2019/8/10
     * @Param [about]
     * @return java.lang.Boolean
     * @Description 添加
     **/
    @Override
    public Boolean addAbout(About about) {
        return aboutMapper.insertSelective(about) > 0;
    }

    @Override
    public About selectById(Integer aboutId) {
        return null;
    }

    @Override
    public Boolean updateAbout(About about) {
        return null;
    }

}

