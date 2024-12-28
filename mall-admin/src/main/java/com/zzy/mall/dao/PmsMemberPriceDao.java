package com.zzy.mall.dao;

import com.zzy.mall.model.PmsMemberPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: mall
 * @description: 会员价格dao层自定义接口
 * @author: zzy
 * @create: 2024-09-17
 */
public interface PmsMemberPriceDao {

    //批量插入数据
    int insertBatch(@Param("list") List<PmsMemberPrice> memberPriceList);

}
