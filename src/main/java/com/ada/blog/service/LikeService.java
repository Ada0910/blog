package com.ada.blog.service;

import com.ada.blog.entity.Like;

import java.util.List;

/**
 * @ClassName:LikeService
 * @author:Ada
 * @date 2020/03/0210:53
 * @Description: 点赞模块的接口层
 */
public interface LikeService {

    void addLikeToRedis(Like like);

    void deleteLikeFromRedis(Like like);

    Integer getLikeTotalFromRedis(Long blogId);

    Integer getLikeTotal(Long blogId);

    List<Like> getLikeListFromRedis();

    void addLikeList();

}
