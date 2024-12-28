package com.zzy.mall.dao;

import com.zzy.mall.model.PmsProductLadder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: mall
 * @description: 创建商品时阶梯价格表-只针对同商品
 * @author: zzy
 * @create: 2024-09-17
 */
public interface PmsProductLadderDao {

    //批量插入
    int insertBatch(@Param("list") List<PmsProductLadder> productLadderList);

}
