package com.ada.blog.mapper;

import com.ada.blog.entity.Role;
import com.ada.blog.util.PageUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :RoleMapper
 * @date 2020/12/8 22:33
 * @Description:
 */
@Component
public interface RoleMapper {
    List<Role> getRoleList(PageUtil pageUtil);

    int getUserListTotal(PageUtil pageUtil);

    int addRole(Role role);

    Role getRoleById(Integer id);

    int deleteBatchRoles(Integer[] ids);

    int updateRole(Role role);
}
