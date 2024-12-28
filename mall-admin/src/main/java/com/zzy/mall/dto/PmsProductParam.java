package com.zzy.mall.dto;

import com.zzy.mall.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: mall
 * @description: 后台商品操作请求参数
 * @author: zzy
 * @create: 2024-08-06
 */
@Data
public class PmsProductParam extends PmsProduct {

    @ApiModelProperty("商品阶梯价格设置")
    //商品阶梯价格设置
    private List<PmsProductLadder> productLadderList;

    @ApiModelProperty("商品满减价格设置")
    //商品满减价格设置
    private List<PmsProductFullReduction> productFullReductionList;

    @ApiModelProperty("商品会员价格设置")
    //商品会员价格设置
    private List<PmsMemberPrice> memberPriceList;

    @ApiModelProperty("商品sku库存信息设置")
    //商品sku库存信息设置
    private List<PmsSkuStock> skuStockList;

    @ApiModelProperty("商品参数和属性信息设置")
    //商品参数和属性信息设置
    private List<PmsProductAttributeValue> productAttributeValueList;

}