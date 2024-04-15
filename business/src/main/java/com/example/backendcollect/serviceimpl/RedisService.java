package com.example.backendcollect.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Rikka
 * @date 2022-03-27 00:41:47
 * @description 直接copy的, 改了一下 cast
 */
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置String键值对
     *
     * @param key
     * @param value
     * @param millis
     */
    public void put(String key, Object value, long millis) {
        redisTemplate.opsForValue().set(key, value, millis, TimeUnit.MINUTES);
    }

    public void putForHash(String objectKey, String hkey, String value) {
        redisTemplate.opsForHash().put(objectKey, hkey, value);
    }

    /**
     * 键-列表
     *
     * @param key
     * @param value
     */
    public void lpush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public <T> List<T> lrange(String key, int from, int to, Class<T> convertTo) {
        List<Object> range = redisTemplate.boundListOps(key).range(from, to);
        assert range != null;
        // 函数式编程的力量. 听说 eclipse collection 很强, 有空学习
        return range.stream().map(convertTo::cast).collect(Collectors.toList());
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(redisTemplate.boundValueOps(key).get());
    }

    public void remove(String key) {
        redisTemplate.delete(key);
    }

    public boolean expire(String key, long millis) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, millis, TimeUnit.MILLISECONDS));
    }

    public boolean persist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    public Long getLong(String key) {
        return (Long) redisTemplate.opsForValue().get(key);
    }

    public Date getDate(String key) {
        return (Date) redisTemplate.opsForValue().get(key);
    }

    /**
     * 对指定key的键值减一
     *
     * @param key
     * @return
     */
    public Long decrBy(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }
}
