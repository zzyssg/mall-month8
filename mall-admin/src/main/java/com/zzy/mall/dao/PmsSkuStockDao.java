package com.zzy.mall.dao;

import com.zzy.mall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: mall
 * @description: 创建商品时dao层的接口
 * @author: zzy
 * @create: 2024-09-17
 */
public interface PmsSkuStockDao {

    //批量新增
    int insertBatch(@Param("list") List<PmsSkuStock> skuStockList);
}
