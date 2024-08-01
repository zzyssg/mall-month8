package com.zzy.mall.service.impl;

import com.zzy.mall.mapper.UmsResourceMapper;
import com.zzy.mall.model.UmsResource;
import com.zzy.mall.model.UmsResourceExample;
import com.zzy.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: mall
 * @description: 资源接口实现类
 * @author: zzy
 * @create: 2024-07-28
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private UmsResourceMapper resourceMapper;
    @Override
    public List<UmsResource> listAll() {
        return resourceMapper.selectByExample(new UmsResourceExample());
    }
}