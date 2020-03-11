package com.ada.blog.service.impl;

import com.ada.blog.entity.Like;
import com.ada.blog.mapper.LikeMapper;
import com.ada.blog.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        synchronized (this) {
            String key = "BLOG:LIKE:" + like.getLikeBlogId();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = like.getLikeCreateTime();
            redisTemplate.opsForHash().put(key, like.getLikeUserIp(), format.format(date));
        }
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
        synchronized (this) {
            String key = "BLOG:LIKE:" + like.getLikeBlogId();
            redisTemplate.opsForHash().delete(key, like.getLikeUserIp());
        }
    }

    /***
     * @Author Ada
     * @Date 17:05 2020/03/03
     * @Param []
     * @return java.lang.Integer
     * @Description 根据bolgId获取缓存中文章的点赞数
     **/
    @Override
    public Integer getLikeTotalFromRedis(Long blogId) {
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
     * @return java.lang.Integer
     * @Author Ada
     * @Date 15:51 2020/03/04
     * @Param [blogId]
     * @Description 从数据库中获取对应文章点赞总数
     **/
    @Override
    public Integer getLikeTotal(Long blogId) {
        if (likeMapper.getLikeTotal(blogId) == null) {
            return 0;
        } else {
            return likeMapper.getLikeTotal(blogId);
        }
    }

    /**
     * @return java.util.List<com.ada.blog.entity.Like>
     * @Author Ada
     * @Date 15:24 2020/03/04
     * @Param [blogId]
     * @Description 获取redis中的所有数据
     **/
    @Override
    public List<Like> getLikeListFromRedis() {
        /**获取Redis中所有key*/
        Set<String> keys = redisTemplate.keys("*");
        /**文章点赞的所有key*/
        List<String> allLikeKeyList = new ArrayList<>();
        keys.forEach(key -> {
            if (key.startsWith("BLOG:LIKE:")) {
                allLikeKeyList.add(key);
            }
        });
        List<Like> likeList = new LinkedList<>();
        allLikeKeyList.forEach(key -> {
            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> map = cursor.next();
                Like like = new Like();
                like.setLikeBlogId(Long.parseLong(key.substring(10)));
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
        });


        return likeList;
    }


    /**
     * @return java.lang.Boolean
     * @Author Ada
     * @Date 15:22 2020/03/04
     * @Param [blogId]
     * @Description 每隔2个钟自动获取数据持久化到数据库
     **/
    @Override
    @Scheduled(cron = "* * 0/2 * * ?")
    public void addLikeList() {
        List<Like> list = getLikeListFromRedis();
        if (list.size() != 0) {
            likeMapper.addLikeList(list);
        }
    }

    /***
     * @Author Ada
     * @Date 23:19 2020/3/10
     * @Param []
     * @return java.util.List<com.ada.blog.entity.Like>
     * @Description 从数据库中查询所有数据
     **/
    @Override
    public List<Like> getLikeList(Long blogId) {
        return likeMapper.getLikeList(blogId);
    }

    /***
     * @Author Ada
     * @Date 23:19 2020/3/10
     * @Param []
     * @return void
     * @Description 从缓存中添加到数据库逐条
     **/
    @Override
    public void addLikeInfo(Like like) {
        likeMapper.addLikeInfo(like);
    }

    /****
     * @Author Ada
     * @Date 22:50 2020/3/11
     * @Param [blogId]
     * @return java.util.List<com.ada.blog.entity.Like>
     * @Description 查询每篇文章的点赞信息
     **/
    @Override
    public List<Like> getLikeListFromRedisByBlogId(Long blogId) {
        String key = "BLOG:LIKE:" + blogId;
        List<Like> likeList = new LinkedList<>();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            Like like = new Like();
            like.setLikeBlogId(Long.parseLong(key.substring(10)));
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

}
