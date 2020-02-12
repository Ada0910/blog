package com.ada.blog.service;

import com.ada.blog.entity.Version;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;

/**
 * @author Ada
 * @ClassName :VersionService
 * @date 2020/2/11 22:22
 * @Description:
 */
public interface VersionService {
    PageResultUtil getVersionPage(PageUtil pageUtil);

    Boolean addVersion(Version version);

    Version selectById(Integer versionId);

    Boolean updateVersion(Version version);

    Boolean deleteBatch(Integer[] ids);
}
