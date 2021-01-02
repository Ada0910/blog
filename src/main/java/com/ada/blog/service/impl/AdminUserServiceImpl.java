package com.ada.blog.service.impl;

import com.ada.blog.entity.AdminUser;
import com.ada.blog.mapper.AdminUserMapper;
import com.ada.blog.service.AdminUserService;
import com.ada.blog.util.MD5Util;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Ada
 * @ClassName :AdminUserServiceImpl
 * @date 2019/6/16 22:33
 * @Description:
 */
@Service("adminUserService")
public
class AdminUserServiceImpl implements AdminUserService {

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
        return false;
    }

    /***
     * @Author Ada
     * @Date 22:37 2020/12/8
     * @Param [pageUtil]
     * @return com.ada.blog.util.PageResultUtil
     * @Description 用户列表
     **/
    @Override
    public PageResultUtil getUserList(PageUtil pageUtil) {
        List<AdminUser> userList = adminUserMapper.getUserList(pageUtil);
        int total = adminUserMapper.getUserListTotal(pageUtil);
        PageResultUtil pageResultUtil = new PageResultUtil(userList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResultUtil;
    }

    /***
     * @Description 添加用户
     *
     * @param user
     * @return java.lang.Boolean
     * @Author Ada
     * @Date 0:00 2021/1/2
     **/
    @Override
    public Boolean addUser(AdminUser user) {
        return adminUserMapper.insertSelective(user) > 0;
    }

    /***
     * @Description 根据主键寻找用户信息
     *
     * @param adminUserId
     * @return com.ada.blog.entity.AdminUser
     * @Author Ada
     * @Date 17:56 2021/1/2
     **/
    @Override
    public AdminUser selectUserById(Integer adminUserId) {
        return adminUserMapper.selectByPrimaryKey(adminUserId);
    }

    /***
     * @Description 更新
     *
     * @param user
     * @return java.lang.Boolean
     * @Author Ada
     * @Date 17:57 2021/1/2
     **/
    @Override
    public Boolean updateUser(AdminUser user) {
        return adminUserMapper.updateByPrimaryKeySelective(user)>0;
    }

    /***
     * @Description  删除
     *
     * @param ids
     * @return boolean
     * @Author Ada
     * @Date 19:02 2021/1/2
     **/
    @Override
    public boolean deleteBatchUser(Integer[] ids) {
        return adminUserMapper.deleteBatchUser(ids)>0;
    }
}
