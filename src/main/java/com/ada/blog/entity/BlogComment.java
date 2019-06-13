package com.ada.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ada
 * @ClassName :BlogComment
 * @date 2019/6/13 22:47
 * @Description: 博客内容评论
 */

@Setter
@Getter
@ToString
public class BlogComment {

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 博客id
     */
    private Long blogId;

    /**
     * 评论者
     */
    private String commentator;

    /**
     * 邮件
     */
    private String email;

    /**
     * 网站的网址
     */
    private String websiteUrl;

    /**
     * 评论内容
     */
    private String commentBody;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentCreateTime;

    /**
     * 评论者的IP地址
     */
    private String commentatorIp;

    /**
     * 回复内容
     */
    private String replyBody;

    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date replyCreateTime;

    /**
     * 评论状态(是否审核通过 0-未审核 1-审核通过)
     */
    private Byte commentStatus;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    private Byte isDeleted;
}
