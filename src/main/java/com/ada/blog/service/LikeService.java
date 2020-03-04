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

    public void addLikeToRedis(Like like);

    public void deleteLikeFromRedis(Like like);

    public Integer getLikeSumFromRedis(Long blogId);

    public List<Like> getLikeListFromRedis(Long blogId);

    public Boolean  addLikeInfo(Long blogId);

    public Integer getLikeSum(Long blogId);


}
