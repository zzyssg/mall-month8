package com.zzy.mall.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * @program: mall
 * @description: 加载动态权限资源的接口
 * @author: zzy
 * @create: 2024-07-25
 */
public interface DynamicSecurityService {

    Map<String, ConfigAttribute> loadDataSource();

}
