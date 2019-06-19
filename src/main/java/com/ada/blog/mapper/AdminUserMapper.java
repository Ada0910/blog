package com.ada.blog.mapper;

import com.ada.blog.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

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

    /**
     * 添加用户
     */
    int insert(AdminUser adminUser);

    int insertSelective(AdminUser adminUser);

    /**
     * 通过adminUserId更新
     */
    int updateByPrimaryKeySelective(AdminUser adminUser);

    int updateByPrimaryKey(AdminUser adminUser);
}
