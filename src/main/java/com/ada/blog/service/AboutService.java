package com.ada.blog.service;

import com.ada.blog.entity.About;
import com.ada.blog.util.PageResultUtil;
import com.ada.blog.util.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Ada
 * @ClassName :AboutService
 * @date 2019/8/10 16:02
 * @Description:
 */
public interface AboutService {

    PageResultUtil getAboutPage(PageUtil pageUtil);

    Boolean addAbout(About about);

    About selectById(Integer aboutId);

    Boolean updateAbout(About about);

    Boolean deleteBitch(Integer[] ids);

    Map<Byte, List<About>> getAboutPage();
}
