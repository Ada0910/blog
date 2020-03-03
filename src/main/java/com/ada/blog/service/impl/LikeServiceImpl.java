package com.ada.blog.service.impl;

import com.ada.blog.entity.Like;
import com.ada.blog.mapper.LikeMapper;
import com.ada.blog.mapper.LinkMapper;
import com.ada.blog.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName:LikeServiceImpl
 * @author: Ada
 * @date 2020/03/02 10:54
 * @Description: 点赞模块业务逻辑层
 */
@Service("likeService")
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @return void
     * @Author Ada
     * @Date 11:36 2020/03/03
     * @Param []
     * @Description 添加点赞信息到缓存数据库
     **/
    @Override
    public void addLikeToRedis(Like like) {
        String key = "BLOG:LIKE:" + like.getLikeBlogId();
        redisTemplate.opsForHash().put(key, like.getLikeUserIp(), like.getLikeCreateTime().toString());

    }

    /**
     * @return void
     * @Author Ada
     * @Date 11:50 2020/03/03
     * @Param [like]
     * @Description 从redis删除点赞者信息
     **/
    @Override
    public void deleteLikeFromRedis(Like like) {
        String key = "BLOG:LIKE:" + like.getLikeBlogId();
        redisTemplate.opsForHash().delete(key, like.getLikeUserIp());
    }

    /***
     * @Author Ada
     * @Date 17:05 2020/03/03
     * @Param []
     * @return java.lang.Integer
     * @Description 获取缓存中的数目
     **/
    @Override
    public Integer getLikeSumFromRedis(Long blogId) {
        Integer sum = 0;
        String key = "BLOG:LIKE:" + blogId;
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            sum++;
           // redisTemplate.opsForHash().delete(key, (String) map.getKey());
        }
        return sum;
    }

    @Override
    public Boolean addLikeInfo(Like like) {
        return likeMapper.addLikeInfo(like)>0;
    }


}
