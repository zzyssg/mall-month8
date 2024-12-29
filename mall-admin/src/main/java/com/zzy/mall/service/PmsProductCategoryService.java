package com.zzy.mall.service;

import com.zzy.mall.dto.PmsProductCategoryParam;
import com.zzy.mall.model.PmsProductCategory;

/**
 * @program: mall
 * @description: 商品分类管理接口
 * @author: zzy
 * @create: 2024-12-22
 */
public interface PmsProductCategoryService {


    int create(PmsProductCategoryParam productCategoryParam);

    PmsProductCategory getItem(Long id);

    int delete(Long id);
}
