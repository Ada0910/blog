package com.ada.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ada
 * @ClassName :Blog
 * @date 2019/6/12 22:35
 * @Description:博客实体类
 */

@Setter
@Getter
@ToString
public class Blog {

    /**
     * 博客id
     */
    private Long blogId;


    /**
     * 博客标题
     */
    private String blogTitle;

    /**
     * 博客自定义路径url
     */
    private String blogSubUrl;

    /**
     * 博客封面图
     */
    private String blogCoverImage;

    /**
     * 博客的分类id
     */
    private Integer blogCategoryId;

    /**
     * 分类名字
     */
    private String blogCategoryName;

    /**
     * 博客标签
     */
    private String blogTags;

    /**
     * 博客的状态(0-草稿 1-发布)
     */
    private Byte blogStatus;

    /**
     * 阅读量
     */
    private Long blogViews;

    /**
     * 是否能评论(0-允许评论 1-不允许评论)
     */
    private Byte enableComment;

    /**
     * 删除( 0=否 1=是)
     */
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 博客内容
     */
    private String blogContent;
}
