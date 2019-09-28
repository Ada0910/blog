package com.ada.blog.service.impl;

import com.ada.blog.entity.About;
import com.ada.blog.mapper.AboutMapper;
import com.ada.blog.service.AboutService;
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

    /***
     * @Author Ada
     * @Date 15:13 2019/8/11
     * @Param [aboutId]
     * @return com.ada.blog.entity.About
     * @Description 根据id查询
     **/
    @Override
    public About selectById(Integer aboutId) {
        return aboutMapper.selectByPrimaryKey(aboutId);
    }

    /***
     * @Author Ada
     * @Date 15:13 2019/8/11
     * @Param [about]
     * @return java.lang.Boolean
     * @Description 更新
     **/
    @Override
    public Boolean updateAbout(About about) {
        return aboutMapper.updateByPrimaryKey(about) > 0;
    }

    /***
     * @Author Ada
     * @Date 15:13 2019/8/11
     * @Param [ids]
     * @return java.lang.Boolean
     * @Description 批量删除
     **/
    @Override
    public Boolean deleteBitch(Integer[] ids) {
        return aboutMapper.deleteBatch(ids) > 0;
    }

    /***
     * @Author Ada
     * @Date 15:13 2019/8/11
     * @Param []
     * @return java.util.Map<java.lang.Byte, java.util.List < com.ada.blog.entity.About>>
     * @Description 首页获取
     **/
    @Override
    public Map<Byte, List<About>> getAboutPage() {
        List<About> abouts = aboutMapper.getAboutList(null);
        if (CollectionUtils.isEmpty(abouts)) {
            return null;
        }
        Map<Byte, List<About>> listMap = abouts.stream().collect(Collectors.groupingBy(About::getAboutType));
        return listMap;

    }
}


