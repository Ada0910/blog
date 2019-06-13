package com.ada.blog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ada
 * @ClassName :BlogTagCount
 * @date 2019/6/13 23:10
 * @Description:博客标签
 */
@Setter
@Getter
@ToString
public class BlogTagCount {

    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 标签的总数
     */
    private Integer tagCount;

}
