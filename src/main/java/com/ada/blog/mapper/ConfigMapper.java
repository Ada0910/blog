package com.ada.blog.mapper;

import com.ada.blog.entity.Config;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :ConfigMapper
 * @date 2019/6/24 22:53
 * @Description:
 */

@Component
public interface ConfigMapper {

    /**
     * 查询出配置的所有信息
     */
    List<Config> selectAll();

    /**
     * 根据配置名查询对应的信息
     */
    Config selectByPrimaryKey(String configName);

    /**
     * 根据配置名更新对应的信息
     */
    int updateByPrimaryKey(Config record);
}
