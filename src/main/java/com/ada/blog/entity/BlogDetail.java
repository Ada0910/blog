package com.ada.blog.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author Ada
 * @ClassName :BlogDetail
 * @date 2019/7/17 23:29
 * @Description:
 */
@Getter
@Setter
public class BlogDetail {

    private Long blogId;

    private String blogTitle;

    private Integer blogCategoryId;

    private Integer commentCount;

    private String blogCategoryIcon;

    private String blogCategoryName;

    private String blogCoverImage;

    private Long blogViews;

    private List<String> blogTags;

    private String blogContent;

    private Byte enableComment;

    private Date createTime;
}
