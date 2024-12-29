package com.zzy.mall.service.impl;

import com.zzy.mall.dao.PmsProductCategoryDao;
import com.zzy.mall.dto.PmsProductCategoryParam;
import com.zzy.mall.mapper.PmsProductCategoryMapper;
import com.zzy.mall.model.PmsProductAttribute;
import com.zzy.mall.model.PmsProductCategory;
import com.zzy.mall.model.PmsProductCategoryAttributeRelation;
import com.zzy.mall.service.PmsProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private PmsProductCategoryDao productCategoryDao;

    @Override
    public int create(PmsProductCategoryParam productCategoryParam) {
        //1、使用参数构建category类
        PmsProductCategory pmsProductCategory = new PmsProductCategory();
        pmsProductCategory.setId(null);
        BeanUtils.copyProperties(productCategoryParam,pmsProductCategory);
        //2、补充category中未在param参数中传递的参数：count、level
        pmsProductCategory.setProductCount(0);
        //  判断level
        setProductCategoryLevel(pmsProductCategory);
        //  插入，获取id，用于加入中间表
        int count = productCategoryMapper.insert(pmsProductCategory);
        //3、用于插入中间表参数(在新建时，关联了一些查询列表，会被设置到中间表里)
        List<PmsProductAttribute> productRelatedAttributeList = productCategoryParam.getProductAttributeList();
        if (!productRelatedAttributeList.isEmpty()) {
            insertRelation(pmsProductCategory.getId(), productRelatedAttributeList);
        }
        return count;
    }

    @Override
    public PmsProductCategory getItem(Long id) {
        return productCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        //检查是否有关联表

    }

    private void insertRelation(Long productCategoryId, List<PmsProductAttribute> productRelatedAttributeList) {
        //构建relationList，调用dao层接口
        List<PmsProductCategoryAttributeRelation> productCategoryAttributeRelationList = new ArrayList<>();
        for (PmsProductAttribute productAttribute : productRelatedAttributeList) {
            PmsProductCategoryAttributeRelation categoryAttributeRelation = new PmsProductCategoryAttributeRelation();
            categoryAttributeRelation.setProductCategoryId(productCategoryId);
            categoryAttributeRelation.setProductAttributeId(productAttribute.getId());
            productCategoryAttributeRelationList.add(categoryAttributeRelation);
        }
        productCategoryDao.insertBatch(productCategoryAttributeRelationList);
    }


    private void setProductCategoryLevel(PmsProductCategory pmsProductCategory) {
        //根据parentId设置level：
        //1、parentId为空，则level为0；
        //2、parentId不为空，则level为parentLevel+1
        Long parentId = pmsProductCategory.getParentId();
        if (parentId == null){
            pmsProductCategory.setLevel(0);
        }else {
            PmsProductCategory parentProductCategory = productCategoryMapper.selectByPrimaryKey(pmsProductCategory.getParentId());
            pmsProductCategory.setLevel(parentProductCategory.getLevel() + 1);
        }
    }
}