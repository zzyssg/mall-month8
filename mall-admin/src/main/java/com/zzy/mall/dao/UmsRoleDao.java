package com.zzy.mall.dao;

import com.zzy.mall.model.UmsRole;

import java.util.List;

/**
 * @program: mall
 * @description: 自定义角色dao层接口
 * @author: zzy
 * @create: 2024-07-29
 */
public interface UmsRoleDao {


    List<UmsRole> getRoleByResourceId(Long resourceId);
}
