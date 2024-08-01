package com.zzy.mall.common.service.impl;

import com.zzy.mall.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: mall
 * @description: 自定义redis实现类
 * @author: zzy
 * @create: 2024-07-21
 */
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time,TimeUnit.SECONDS);
    }

    @Override
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public void expire(String key, long time) {
        redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key,delta);
    }

    @Override
    public long dec(String key,long delta) {
        return redisTemplate.opsForValue().increment(key,-delta);
    }

    @Override
    public void hPut(String key, String hashKey, Object value, long time) {
        redisTemplate.opsForHash().put(key,hashKey,value);
        redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    @Override
    public void hPut(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    @Override
    public void hPutAll(String key, Map<Object, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key,map);
        redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    @Override
    public void hPutAll(String key, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(key,map);
    }

    @Override
    public void hDel(String key, String... hashKey) {
        redisTemplate.opsForHash().delete(key,hashKey);
    }

    @Override
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key,hashKey);
    }

    @Override
    public boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key,hashKey);
    }

    @Override
    public long hIncr(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key,hashKey,delta);
    }

    @Override
    public long hDec(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key,hashKey,-delta);
    }

    @Override
    public void lPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key,value);
    }

    @Override
    public void lPush(String key, Object value, long time) {
        redisTemplate.opsForList().rightPush(key,value);
        redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    @Override
    public void lPush(String key, Object... value) {
        redisTemplate.opsForList().rightPushAll(key,value);
    }

    @Override
    public void lPush(String key, long time, Object... value) {
        redisTemplate.opsForList().rightPushAll(key,value);
        redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    @Override
    public Long lDel(String key, int count, Object value) {
        return redisTemplate.opsForList().remove(key,count,value);
    }

    @Override
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key,index);
    }

    @Override
    public long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key,start,end);
    }

    @Override
    public void sAdd(String key, Object... value) {
        redisTemplate.opsForSet().add(key,value);
    }

    @Override
    public void sAdd(String key, long time, Object... value) {
        redisTemplate.opsForSet().add(key,value);
        redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    @Override
    public Long sRemove(String key, Object... value) {
        return redisTemplate.opsForSet().remove(key,value);
    }

    @Override
    public long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key,value);
    }

    @Override
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}