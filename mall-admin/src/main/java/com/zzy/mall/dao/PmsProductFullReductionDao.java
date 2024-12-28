package com.zzy.mall.dao;

import com.zzy.mall.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: mall
 * @description: 创建商品时满减自定义dao层接口
 * @author: zzy
 * @create: 2024-09-17
 */
public interface PmsProductFullReductionDao {

    //批量插入满减策略
    int insertBatch(@Param("list") List<PmsProductFullReduction> productFullReductionList);

}
