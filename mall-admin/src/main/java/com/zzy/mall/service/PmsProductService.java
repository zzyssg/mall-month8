package com.zzy.mall.service;

import com.zzy.mall.dto.PmsProductParam;
import com.zzy.mall.dto.PmsProductQueryParam;
import com.zzy.mall.dto.PmsProductResult;
import com.zzy.mall.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: mall
 * @description: 商品后台管理接口
 * @author: zzy
 * @create: 2024-08-06
 */
public interface PmsProductService {
    //添加事务
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int create(PmsProductParam productParam);

    List<PmsProduct> list(PmsProductQueryParam queryParam, Integer pageNum, Integer pageSize);

    List<PmsProduct> simpleList(String keyword);

    int update(Long id, PmsProductParam productParam);

    int updateDeleteStatus(List<Long> ids, Integer status);

    int updateNewStatus(List<Long> ids, Integer status);

    int updatePublishStatus(List<Long> ids, Integer status);

    int updateRecommendStatus(List<Long> ids, Integer status);

    int updateVerifyStatus(List<Long> ids, Integer status);

    PmsProductResult getUpdateInfo(Long id);
}
