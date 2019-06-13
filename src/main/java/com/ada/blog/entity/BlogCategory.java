package com.ada.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ada
 * @ClassName :BlogCategory
 * @date 2019/6/13 22:42
 * @Description:博客分类实体类
 */

@Setter
@Getter
@ToString
public class BlogCategory {

    /**
     * 博客分类id
     */
    private Integer categoryId;

    /**
     * 分类名字
     */
    private String categoryName;

    /**
     * 图标
     */
    private String categoryIcon;

    /**
     * 分类的排序值 被使用的越多数值越大
     */
    private Integer categoryRank;

    /**
     * 是否删除 0=否 1=是
     */
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
