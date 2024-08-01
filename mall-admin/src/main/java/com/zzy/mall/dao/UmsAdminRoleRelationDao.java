package com.zzy.mall.dao;

import com.zzy.mall.model.UmsAdminRoleRelation;

import java.util.List;

/**
 * @program: mall
 * @description: 用户角色关联表接口
 * @author: zzy
 * @create: 2024-07-28
 */
public interface UmsAdminRoleRelationDao {
    int insertBatch(List<UmsAdminRoleRelation> relations);
}
