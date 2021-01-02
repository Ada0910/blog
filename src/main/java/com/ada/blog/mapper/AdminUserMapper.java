package com.ada.blog.mapper;

import com.ada.blog.entity.AdminUser;
import com.ada.blog.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :AdminUserMapper
 * @date 2019/6/12 22:40
 * @Description:
 */

@Component
public interface AdminUserMapper {

    /**
     * 登陆
     */
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过adminUserId查询
     */
    AdminUser selectByPrimaryKey(Integer adminUserId);


    int insertSelective(AdminUser adminUser);

    /**
     * 通过adminUserId更新
     */
    int updateByPrimaryKeySelective(AdminUser adminUser);


    /**获取用户列表*/
    List<AdminUser> getUserList(PageUtil pageUtil);

    /**获取用户条数*/
    int getUserListTotal(PageUtil pageUtil);

    Boolean addUser(AdminUser user);

    int deleteBatchUser(Integer[] ids);
}
