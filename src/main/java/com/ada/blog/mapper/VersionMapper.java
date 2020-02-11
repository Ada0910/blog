package com.ada.blog.mapper;

import com.ada.blog.entity.Version;
import com.ada.blog.util.PageUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :VersionMapper
 * @date 2020/2/11 22:28
 * @Description:
 */
@Component
public interface VersionMapper {
    List<Version> getVersionList(PageUtil pageUtil);

    Integer getTotalList(PageUtil pageUtil);

    Integer insertVersionSelective(Version version);
}
