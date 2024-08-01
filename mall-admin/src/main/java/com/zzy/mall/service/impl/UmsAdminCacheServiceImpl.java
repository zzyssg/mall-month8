package com.zzy.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.zzy.mall.common.service.RedisService;
import com.zzy.mall.dao.UmsAdminDao;
import com.zzy.mall.dao.UmsRoleDao;
import com.zzy.mall.dao.UmsRoleResourceRelationDao;
import com.zzy.mall.mapper.UmsAdminRoleRelationMapper;
import com.zzy.mall.model.*;
import com.zzy.mall.service.UmsAdminCacheService;
import com.zzy.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mall
 * @description: redis缓存接口实现类
 * @author: zzy
 * @create: 2024-07-29
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

    @Value("${redis.expiration.common}")
    private Long expiration;

    @Value("${redis.key.admin}")
    private   String REDIS_KEY_ADMIN;

    @Value("${redis.key.resourceList}")
    private   String REDIS_KEY_RESOURCE_LIST;

    @Value("${redis.database}")
    private String REDIS_MALL;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsRoleResourceRelationDao roleResourceRelationDao;

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    //TODO
    @Override
    public void delAdmin(Long adminId) {
        UmsAdmin admin = adminService.getAdmin(adminId);
        if (admin != null){
            String key = REDIS_MALL + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceList(Long adminId) {
        String key = REDIS_MALL + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.del(key);
    }

    @Override
    public void delResourceListByRoleId(Long roleId) {
        String keyPrefix = REDIS_MALL + ":" +  REDIS_KEY_RESOURCE_LIST + ":";
        UmsAdminRoleRelationExample adminRoleRelationExample = new UmsAdminRoleRelationExample();
        adminRoleRelationExample.createCriteria().andRoleIdEqualTo(roleId);
        List<UmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectByExample(adminRoleRelationExample);
        if (CollUtil.isEmpty(relationList)){
            return;
        }
        List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
        redisService.del(keys);

    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        String keyPrefix = REDIS_MALL + ":" +  REDIS_KEY_RESOURCE_LIST + ":";
        UmsAdminRoleRelationExample adminRoleRelationExample = new UmsAdminRoleRelationExample();
        adminRoleRelationExample.createCriteria().andRoleIdIn(roleIds);
        List<UmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectByExample(adminRoleRelationExample);
        if (CollUtil.isEmpty(relationList)){
            return;
        }
        List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
        redisService.del(keys);
    }

    @Override
    public void delResourceListByResourceId(Long resourceId) {
        String keyPrefix = REDIS_MALL + ":" + REDIS_KEY_RESOURCE_LIST + ":";
        //resourceId -> role ,根据roleId删除
        List<Long> adminIdList = roleResourceRelationDao.getAdminIdList(resourceId);
        if (CollUtil.isEmpty(adminIdList)){
            return;
        }
        List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
        redisService.del(keys);
    }

    @Override
    public UmsAdmin getAdmin(String username) {
        String key = REDIS_MALL + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = REDIS_MALL + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key,admin,expiration);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        String key =REDIS_MALL + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResource>) redisService.get(key);
    }

    @Override
    public void setAdmin(List<UmsResource> resourceList, Long adminId) {
        String key = REDIS_MALL + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key,resourceList,expiration);

    }
}