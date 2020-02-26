package com.ada.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName:RedisUtil
 * @author: Ada
 * @date 2020/02/25 15:22
 * @Description: Redis操作类
 */
public class RedisUtil {

    @Autowired
    private static RedisTemplate<String, Object> redisTemplate;

    /**
     * @return boolean
     * @Author Ada
     * @Date 15:24 2020/02/25
     * @Param [key, time]
     * @Description 设置缓存过期时间
     **/
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return long
     * @Author Ada
     * @Date 15:25 2020/02/25
     * @Param [key]
     * @Description 获取缓存过期时间
     **/
    public static long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * @return boolean
     * @Author Ada
     * @Date 15:27 2020/02/25
     * @Param [key]
     * @Description 判断key是否存在
     **/
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return java.lang.Object
     * @Author Ada
     * @Date 15:27 2020/02/25
     * @Param [key]
     * @Description 根据key或者值
     **/
    public static Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * @return boolean
     * @Author Ada
     * @Date 15:28 2020/02/25
     * @Param [key, value]
     * @Description 设置值
     **/
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return boolean
     * @Author Ada
     * @Date 15:32 2020/02/25
     * @Param [key, map, time]
     * @Description hash批量设置并设置过期时间
     **/
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return java.util.Map<java.lang.Object, java.lang.Object>
     * @Author Ada
     * @Date 15:34 2020/02/25
     * @Param [key]
     * @Description hash批量获取值
     **/
    public static Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /***
     * @Author Ada
     * @Date 22:26 2020/2/26
     * @Param [key, hashKey]
     * @return java.lang.String
     * @Description hash类型get值
     **/
    public static String hget(String key, String item) {
        return (String) redisTemplate.opsForHash().get(key, item);
    }
}
