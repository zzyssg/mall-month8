package com.zzy.mall.dao;

import com.zzy.mall.model.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName PmsProductCategoryDao
 * @Author ZZy
 * @Date 2024/12/29 11:03
 * @Description
 * @Version 1.0
 */
public interface PmsProductCategoryDao {


    void insertBatch(@Param("list") List<PmsProductCategoryAttributeRelation> productCategoryAttributeRelationList);

}
