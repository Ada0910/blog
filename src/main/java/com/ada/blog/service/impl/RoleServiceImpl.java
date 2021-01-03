package com.ada.blog.service.impl;

import com.ada.blog.entity.Role;
import com.ada.blog.mapper.RoleMapper;
import com.ada.blog.service.RoleService;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ada
 * @ClassName :RoleServiceImpl
 * @date 2020/12/8 22:27
 * @Description: 角色管理
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    /***
     * @Description 分页获取数据
     *
     * @param pageUtil
     * @return com.ada.blog.util.PageResultUtil
     * @Author Ada
     * @Date 20:24 2021/1/3
     **/
    @Override
    public PageResultUtil getRoleList(PageUtil pageUtil) {
        List<Role> userList = roleMapper.getRoleList(pageUtil);
        int total = roleMapper.getUserListTotal(pageUtil);
        PageResultUtil pageResultUtil = new PageResultUtil(userList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResultUtil;
    }

    /***
     * @Description 添加
     *
     * @param role
     * @return java.lang.Boolean
     * @Author Ada
     * @Date 20:26 2021/1/3
     **/
    @Override
    public Boolean addrole(Role role) {
        return roleMapper.addRole(role) > 0;
    }

    @Override
    public Role selectRoleById(Integer id) {
        return roleMapper.getRoleById(id);
    }

    @Override
    public Boolean updaterole(Role role) {
       return roleMapper.updateRole(role)>0;
    }

    @Override
    public boolean deleteBatchRole(Integer[] ids) {
        return roleMapper.deleteBatchRoles(ids)>0;
    }

}