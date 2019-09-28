package com.ada.blog.mapper;

import com.ada.blog.entity.About;
import com.ada.blog.util.PageUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ada
 * @ClassName :AboutMapper
 * @date 2019/8/10 10:40
 * @Description:
 */
@Component
public interface AboutMapper {

    List<About> getAboutList(PageUtil pageUtil);

    Integer getTotalAbouts(PageUtil pageUtil);

    Integer insertSelective(About about);

    About selectByPrimaryKey(Integer aboutId);

    Integer updateByPrimaryKey(About about);

    Integer deleteBatch(Integer[] ids);
}
