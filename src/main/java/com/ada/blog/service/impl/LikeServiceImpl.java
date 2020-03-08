package com.ada.blog.service.impl;

import com.ada.blog.entity.Like;
import com.ada.blog.mapper.LikeMapper;
import com.ada.blog.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = like.getLikeCreateTime();
        redisTemplate.opsForHash().put(key, like.getLikeUserIp(), format.format(date));
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
     * @Description 根据blogId 获取缓存中的数目
     **/
    @Override
    public Integer getLikeSumFromRedis(Long blogId) {
        Integer sum = 0;
        String key = "BLOG:LIKE:" + blogId;
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            sum++;
        }
        return sum;
    }

    /**
     * @return java.util.List<com.ada.blog.entity.Like>
     * @Author Ada
     * @Date 15:24 2020/03/04
     * @Param [blogId]
     * @Description 获取redis中的数据
     **/
    @Override
    public List<Like> getLikeListFromRedis(Long blogId) {
        List<Like> likeList = new LinkedList<>();
        String key = "BLOG:LIKE:" + blogId;
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            Like like = new Like();
            like.setLikeBlogId(blogId);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            like.setLikeUserIp((String) map.getKey());
            String date = (String) map.getValue();
            try {
                like.setLikeCreateTime(format.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            likeList.add(like);
            redisTemplate.opsForHash().delete(key, map.getKey());
        }

        return likeList;
    }

    /**
     * @return java.lang.Boolean
     * @Author Ada
     * @Date 15:22 2020/03/04
     * @Param [blogId]
     * @Description 获取数据持久化到数据库
     **/
    @Override
    public Boolean addLikeInfo(Long blogId) {
        List<Like> list = getLikeListFromRedis(blogId);
        return likeMapper.addLikeInfo(list) > 0;
    }

    /**
     * @return java.lang.Integer
     * @Author Ada
     * @Date 15:51 2020/03/04
     * @Param [blogId]
     * @Description 从数据库中获取对应用户的总数
     **/
    @Override
    public Integer getLikeSum(Long blogId) {
        if (likeMapper.getLikeSum(blogId) == null) {
            return 0;
        } else {
            return likeMapper.getLikeSum(blogId);
        }
    }


}
