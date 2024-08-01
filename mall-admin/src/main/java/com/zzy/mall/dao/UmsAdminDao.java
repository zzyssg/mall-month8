package com.zzy.mall.dao;

import com.zzy.mall.model.UmsAdmin;
import com.zzy.mall.model.UmsResource;
import com.zzy.mall.model.UmsRole;

import java.util.List;

/**
 * @program: mall
 * @description: 后台用户dao层接口
 * @author: zzy
 * @create: 2024-07-28
 */
public interface UmsAdminDao {


    List<UmsResource> getResourceByAdminId(Long adminId);

    List<UmsRole> getRoleByAdminId(Long adminId);

    List<UmsAdmin> getAdminListByRoleId(Long roleId);

    List<UmsAdmin> getAdminListByRoleIds(List<Long> roleIds);
}
