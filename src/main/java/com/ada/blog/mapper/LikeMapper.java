package com.ada.blog.mapper;

import com.ada.blog.entity.Like;
import org.springframework.stereotype.Component;

/**
 * @author Ada
 * @ClassName :LikeMapper
 * @date 2020/3/3 23:21
 * @Description:
 */
@Component
public interface LikeMapper {
    Integer addLikeInfo(Like like);
}
