package com.ada.blog.service.impl;

import com.ada.blog.entity.Version;
import com.ada.blog.mapper.VersionMapper;
import com.ada.blog.service.VersionService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ada
 * @ClassName :VersionServiceImpl
 * @date 2020/2/11 22:23
 * @Description:
 */
@Service("versionService")
public class VersionServiceImpl implements VersionService {

    /***
     * @Author Ada
     * @Date 22:55 2020/2/11
     * @Param
     * @return
     * @Description 查看所有更新版本信息
     **/
    @Autowired
    private VersionMapper versionMapper;

    @Override
    public PageResultUtil getVersionPage(PageUtil pageUtil) {
        List<Version> versionList = versionMapper.getVersionList(pageUtil);
        int total = versionMapper.getTotalList(pageUtil);
        PageResultUtil pageResultUtil = new PageResultUtil(versionList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResultUtil;
    }

    /***
     * @Author Ada
     * @Date 23:21 2020/2/11
     * @Param [version]
     * @return java.lang.Boolean
     * @Description 添加版本信息
     **/
    @Override
    public Boolean addVersion(Version version) {
        return versionMapper.insertVersionSelective(version)>0;
    }


}
