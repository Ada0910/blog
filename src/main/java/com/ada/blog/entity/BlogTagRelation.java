package com.ada.blog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ada
 * @ClassName :BlogTagRelation
 * @date 2019/6/13 23:02
 * @Description:博客标签关系表
 */

@Setter
@Getter
@ToString
public class BlogTagRelation {
    /**
     * 关系表id
     */
    private Long relationId;

    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 添加时间
     */
    private Date createTime;
}
