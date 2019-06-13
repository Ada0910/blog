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
     * 用户id
     */
    private Integer adminUserId;
    /**
     * 登陆名
     */
    private String loginUserName;
    /**
     * 密码
     */
    private String loginPassword;
    /**
     * 绰号
     */
    private String nickName;
    private Byte locked;
    /**
     * 创建时间
     */
    private Date createTime;

}
