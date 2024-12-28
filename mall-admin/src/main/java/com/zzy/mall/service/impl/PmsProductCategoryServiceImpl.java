package com.zzy.mall.service.impl;

import com.zzy.mall.dto.PmsProductCategoryParam;
import com.zzy.mall.mapper.PmsProductCategoryMapper;
import com.zzy.mall.model.PmsProductCategory;
import com.zzy.mall.service.PmsProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: mall
 * @description: 商品分类管理接口实现类
 * @author: zzy
 * @create: 2024-12-22
 */
@Service
@Slf4j
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;

    @Override
    public int create(PmsProductCategoryParam productCategoryParam) {
        //1、使用参数构建category类
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setId(null);
        BeanUtils.copyProperties(pmsProductCategory,productCategoryParam);
        //2、补充category中未在param参数中传递的参数：count、level
        pmsProductCategory.setProductCount(0);
        //  判断level
        setProductCategoryLevel(pmsProductCategory);
        //  插入，获取id，用于加入中间表
        int count = productCategoryMapper.insert(pmsProductCategory);
        //3、用于插入中间表参数
        insertRelation(pmsProductCategory,productCategoryParam.getProductAttributeList());
        PmsProductCategoryServiceImpl
        return count;
    }
}