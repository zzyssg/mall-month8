package com.zzy.mall.service;

import com.zzy.mall.model.UmsAdmin;
import com.zzy.mall.model.UmsResource;

import java.util.List;

/**
 * @program: mall
 * @description: redis缓存接口
 * @author: zzy
 * @create: 2024-07-29
 */
public interface UmsAdminCacheService {

    //清除缓存
    void delAdmin(Long adminId);
    void delResourceList(Long adminId);

    void delResourceListByRoleId(Long roleId);
    void delResourceListByRoleIds(List<Long> roleIds);
    void delResourceListByResourceId(Long resourceId);


    //设置缓存
    UmsAdmin getAdmin(String username);
    void setAdmin(UmsAdmin admin);

    List<UmsResource> getResourceList(Long adminId);
    void setAdmin(List<UmsResource> resourceList, Long adminId);


}
