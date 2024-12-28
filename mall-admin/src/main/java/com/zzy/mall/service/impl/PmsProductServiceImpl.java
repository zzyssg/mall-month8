package com.zzy.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zzy.mall.dao.*;
import com.zzy.mall.dto.PmsProductParam;
import com.zzy.mall.dto.PmsProductQueryParam;
import com.zzy.mall.dto.PmsProductResult;
import com.zzy.mall.mapper.PmsProductMapper;
import com.zzy.mall.model.PmsMemberPrice;
import com.zzy.mall.model.PmsProduct;
import com.zzy.mall.model.PmsProductExample;
import com.zzy.mall.model.PmsSkuStock;
import com.zzy.mall.service.PmsProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @program: mall
 * @description: 商品后台管理接口实现类
 * @author: zzy
 * @create: 2024-08-06
 */
@Service
@Slf4j
public class PmsProductServiceImpl implements PmsProductService {


    @Autowired
    private PmsProductDao productDao;

    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private PmsProductLadderDao productLadderDao;

    @Autowired
    private PmsProductFullReductionDao productFullReductionDao;

    @Autowired
    private PmsMemberPriceDao memberPriceDao;
    @Autowired
    private PmsProductAttributeValueDao productAttributeValueDao;

    @Autowired
    private PmsSkuStockDao productSkuStockDao;


    @Override
    public int create(PmsProductParam productParam) {
//        PmsProduct product = new PmsProduct();
        //根据参数首先创建商品，然后根据商品id及关联参数，创建其他关联表信息
        PmsProduct product = productParam;
        product.setId(null);
        int count = productMapper.insertSelective(product);
        //根据id和其他参数，依次生成其他数据
        relateAndInsertList(productLadderDao,productParam.getProductLadderList(),product.getId());
        relateAndInsertList(productFullReductionDao,productParam.getProductFullReductionList(),product.getId());
        relateAndInsertList(productAttributeValueDao,productParam.getProductAttributeValueList(),product.getId());
        relateAndInsertList(memberPriceDao,productParam.getMemberPriceList(),product.getId());
        relateAndInsertList(productSkuStockDao,productParam.getSkuStockList(), product.getId());
        return count;

    }

    //使用反射获取方法对象:谁 调用 什么方法，参数类型
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            //设置dataList的id为null，设置dataList的productId为productId
            for(Object data:dataList){
                Method setId = data.getClass().getMethod("setId", Long.class);
                setId.invoke(data,(Long)null);
                Method setProductId = data.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(data,productId);
            }
            //调用dao的批量插入方法
            Method insertBatch = dao.getClass().getMethod("insertBatch", List.class);
            insertBatch.invoke(dao,dataList);
        }catch (Exception e){
            log.error("创建商品时出错：",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<PmsProduct> list(PmsProductQueryParam queryParam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        //TODO 这里是否有必要先判断为空，如果前端不设置为空，且前端传入的字段全为空，此时，controller接收到的此对象是null吗？
        if (queryParam != null) {
            if (!StrUtil.isEmpty(queryParam.getKeyword())){
                criteria.andKeywordsLike("%"+ queryParam.getKeyword() + "%");
            }
            if (queryParam.getPublishStatus() != null){
                criteria.andPublishStatusEqualTo(queryParam.getPublishStatus());
            }
            if (queryParam.getNewStatus() != null){
                criteria.andNewStatusEqualTo(queryParam.getNewStatus());
            }
            if (queryParam.getVerifyStatus() != null){
                criteria.andVerifyStatusEqualTo(queryParam.getVerifyStatus());
            }
        }
        return productMapper.selectByExample(example);
    }

    @Override
    public List<PmsProduct> simpleList(String keyword) {
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        return productMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, PmsProductParam productParam) {
        PmsProduct product = new PmsProduct();
        BeanUtils.copyProperties(productParam, product);
        product.setId(id);
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer status) {
        PmsProduct product = new PmsProduct();
        product.setDeleteStatus(status);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids).andDeleteStatusEqualTo(0);
        return productMapper.updateByExampleSelective(product, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer status) {
        PmsProduct product = new PmsProduct();
        product.setNewStatus(status);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids).andDeleteStatusEqualTo(0);
        return productMapper.updateByExampleSelective(product,example);
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer status) {
        PmsProduct product = new PmsProduct();
        product.setPublishStatus(status);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids).andDeleteStatusEqualTo(0);
        return productMapper.updateByExampleSelective(product, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer status) {
        PmsProduct product = new PmsProduct();
        product.setRecommandStatus(status);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids).andDeleteStatusEqualTo(0);
        return productMapper.updateByExampleSelective(product, example);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer status) {
        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(status);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids).andDeleteStatusEqualTo(0);
        return productMapper.updateByExampleSelective(product, example);
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        //TODO 根据id获取物品信息

        return productDao.getUpdateInfo(id);
    }
}