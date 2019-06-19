package com.ada.blog.service;

import com.ada.blog.entity.AdminUser;

/**
 * 用户登陆service
 */
public interface AdminUserService {

    AdminUser login(String userName, String password);

    /**
     * 获取用户信息
     **/
    AdminUser getUserDetailById(Integer loginUserId);

    /**
     * 修改当前登陆用户的密码
     **/
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    /***
     * 修改当前登陆用户的名字信息
     **/
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);
}
