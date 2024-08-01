package com.zzy.mall.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: mall
 * @description: 自定义redis实现类
 * @author: zzy
 * @create: 2024-07-21
 */
public interface RedisService {


    //普通类 增2 删2 改1 查3 增长2
    void set(String key, Object value);
    void set(String key,Object value,long time);
    boolean del(String key);
    Long del(List<String> keys);

    void expire(String key,long time);
    long getExpire(String key);
    Object get(String key);
    boolean hasKey(String key);

    long incr(String key,long delta);
    long dec(String key,long delta);

    //hash 增4 删1 改0 查3 增长2
    void hPut(String key,String hashKey,Object value,long time);
    void hPut(String key,String hashKey,Object value);
    void hPutAll(String key,Map<Object,Object> map,long time);
    void hPutAll(String key,Map<Object,Object> map);

    void hDel(String key,String... hashKey);

    Map<Object,Object> hGetAll(String key);
    Object hGet(String key,String hashKey);

    boolean hHasKey(String key,String hashKey);

    long hIncr(String key,String hashKey,long delta);
    long hDec(String key,String hashKey,long delta);

    //list 增4 删1 改0 查3
    void lPush(String key,Object value);
    void lPush(String key,Object value,long time);
    void lPush(String key,Object... value);
    void lPush(String key,long time,Object... value);

    Long lDel(String key, int count , Object value);

    Object lIndex(String key,long index);
    long lSize(String key);
    List<Object> lRange(String key,long start,long end);

    //set 增2 删1 改0 查3
    void sAdd(String key,Object... value);
    void sAdd(String key,long time,Object... value);

    Long sRemove(String key, Object... value);

    long sSize(String key);
    boolean sIsMember(String key,Object value);
    Set<Object> sMembers(String key);



}
