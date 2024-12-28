package com.zzy.mall.dao;

import com.zzy.mall.model.PmsProductAttributeValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: mall
 * @description: 创建商品时，属性和参数的dao层自定义接口
 * @author: zzy
 * @create: 2024-09-17
 */
public interface PmsProductAttributeValueDao {

    //批量插入
    int insertBatch(@Param("list") List<PmsProductAttributeValue> productAttributeValueList);

}
