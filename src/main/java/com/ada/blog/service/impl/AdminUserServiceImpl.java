package com.ada.blog.service.impl;

import com.ada.blog.entity.AdminUser;
import com.ada.blog.mapper.AdminUserMapper;
import com.ada.blog.service.AdminUserService;
import com.ada.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Ada
 * @ClassName :AdminUserServiceImpl
 * @date 2019/6/16 22:33
 * @Description:
 */
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * 用户名和密码登陆
     */
    @Override
    public AdminUser login(String userName, String password) {
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        return adminUserMapper.login(userName, passwordMD5);
    }

    /**
     * 根据登陆id获取用户信息
     */
    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {

        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    /**
     * 更新密码
     */
    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {

        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        /**当前用户非空才能进行更改*/
        if (adminUser != null) {
            String originPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            /**对比原始密码是否正确*/
            if (originPasswordMd5.equals(adminUser.getLoginPassword())) {
                adminUser.setLoginPassword(newPasswordMd5);
                if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 更新名字
     */
    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {

        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        /**当前用户非空才可以进行更改*/
        if (adminUser != null) {
            //设置新密码并修改
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                //修改成功则返回true
                return true;
            }
        }
        return null;
    }
}
