package com.zzy.mall.dao;

import java.util.List;

/**
 * @program: mall
 * @description: role和resource关联对象dao层接口
 * @author: zzy
 * @create: 2024-07-29
 */
public interface UmsRoleResourceRelationDao {


    List<Long> getAdminIdList(Long resourceId);
}
