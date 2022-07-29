package com.nott.pms.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author Zouwenlong
 * Date on 2022/1/28 10:03
 */
@Component
public class CacheStorage {

    @Value("${spring.application.name}")
    private String applicationName;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public CacheStorage() {
    }

    public <T> T put(T value, String hashKey, String id) {
        String key = id;
        this.redisTemplate.opsForHash().put(key, this.getHashkey(hashKey), value);
        this.redisTemplate.expire(key, 3600, TimeUnit.SECONDS);
        return value;
    }

    public <T> T put(T value, String hashKey, String id, long timeout) {
        String key = id;
        this.redisTemplate.opsForHash().put(key, this.getHashkey(hashKey), value);
        this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return value;
    }

    public <T> T get(String hashKey, String id) {
        return (T) this.redisTemplate.opsForHash().get(id, this.getHashkey(hashKey));
    }

    public void expire(String id) {
        this.redisTemplate.expire(id, 1L, TimeUnit.SECONDS);
    }

    public void refresh(String id) {
        this.redisTemplate.expire(id, 3600L, TimeUnit.SECONDS);
    }

    private String getHashkey(String hashKey) {
        return this.applicationName + "::" + hashKey;
    }
}
