package com.zzy.mall.dao;

import com.zzy.mall.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: mall
 * @description: 商品后台管理dao层接口
 * @author: zzy
 * @create: 2024-08-06
 */
public interface PmsProductDao {

    PmsProductResult getUpdateInfo(@Param("id") Long id);
}