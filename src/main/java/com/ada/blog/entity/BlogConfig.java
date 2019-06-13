package com.ada.blog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ada
 * @ClassName :BlogConfig
 * @date 2019/6/13 23:05
 * @Description:博客配置项实体
 */
@Setter
@Getter
@ToString
public class BlogConfig {
    /**
     * 配置项的名称
     */
    private String configName;

    /**
     * 配置项的值
     */
    private String configValue;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
