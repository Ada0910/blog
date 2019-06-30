package com.ada.blog.mapper;

import com.ada.blog.entity.Link;
import com.ada.blog.util.PageUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :LinkMapper
 * @date 2019/6/29 22:23
 * @Description: 友情链接Dao
 */
@Component
public interface LinkMapper {

    /**
     * 根据主键查询
     */
    Link selectByPrimaryKey(Integer id);

    /**
     * 根据主键删除
     */
    int deleteByPrimaryKey(Integer linkId);

    /**
     * 批量删除
     */
    int deleteBatch(Integer[] ids);

    /**
     * 查询并倒叙排序
     */
    List<Link> findLinkList(PageUtil pageUtil);

    /**
     * 获取总数
     */
    Integer getTotalLinks(PageUtil pageUtil);

    /**
     * 直接插入
     */
    int insert(Link link);

    /**
     * 插入，有判断是否为空
     */
    int insertSelective(Link link);

    /**
     * 更新，有判断是否为空
     */
    int updateByPrimaryKey(Link link);

    /**
     * 直接更新
     */
    int update(Link link);

}
