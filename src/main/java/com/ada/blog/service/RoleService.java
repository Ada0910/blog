package com.ada.blog.service;

import com.ada.blog.entity.Role;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;

/**
 * @author Ada
 * @ClassName :RoleService
 * @date 2020/12/8 22:25
 * @Description:
 */
public interface RoleService {
    PageResultUtil getRoleList(PageUtil pageUtil);

    Boolean addrole(Role role);

    Role selectRoleById(Integer id);

    Boolean updaterole(Role role);

    boolean deleteBatchRole(Integer[] ids);
}
