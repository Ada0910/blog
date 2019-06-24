package com.ada.blog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Ada
 * @ClassName :AdminUser
 * @date 2019/6/11 23:45
 * @Description:后台用户实体类
 */

@Setter
@Getter
@ToString
public class AdminUser {

    /**
     * 管理员id
     */
    private Integer adminUserId;
    /**
     * 管理员登陆名称
     */
    private String loginUserName;
    /**
     * 管理员登陆密码
     */
    private String loginPassword;
    /**
     * 管理员显示昵称
     */
    private String nickName;
    /**
     * 是否锁定 0未锁定 1已锁定无法登陆
     */
    private Byte locked;
    /**
     * 创建时间
     */
    private Date createTime;

}
