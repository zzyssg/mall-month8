package com.zzy.mall.dto;

import com.zzy.mall.model.PmsProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: mall
 * @description: 获取商品编辑时信息
 * @author: zzy
 * @create: 2024-08-06
 */
@Data
public class PmsProductResult extends PmsProductParam {

    @ApiModelProperty("商品分类的上级id")
    private Long cateParentId;

}