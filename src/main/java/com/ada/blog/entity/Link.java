package com.ada.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ada
 * @ClassName :Link
 * @date 2019/6/13 23:07
 * @Description:友情链接实体
 */

@Setter
@Getter
@ToString
public class Link {
    /**
     * 友链表主键id
     */
    private Integer linkId;

    /**
     * 友链类别 0-友链 1-推荐 2-个人网站
     */
    private Byte linkType;

    /**
     * 网站名称
     */
    private String linkName;

    /**
     * 网站链接
     */
    private String linkUrl;

    /**
     * 网站描述
     */
    private String linkDescription;


    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
