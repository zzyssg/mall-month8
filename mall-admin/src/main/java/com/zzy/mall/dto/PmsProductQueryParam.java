package com.zzy.mall.dto;

import com.zzy.mall.model.PmsProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: mall
 * @description: 商品查询参数
 * @author: zzy
 * @create: 2024-09-17
 */
@Data
public class PmsProductQueryParam {


    @ApiModelProperty("上架状态：0->下架；1->上架")
    private Integer publishStatus;

    @ApiModelProperty("新品状态:0->不是新品；1->新品")
    private Integer newStatus;

    @ApiModelProperty("审核状态：0->未审核；1->审核通过")
    private Integer verifyStatus;

    @ApiModelProperty("关键字模糊查询")
    private String keyword;


}