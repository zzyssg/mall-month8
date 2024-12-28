package com.zzy.mall.dto;

import com.zzy.mall.model.PmsProduct;
import com.zzy.mall.model.PmsProductAttribute;
import com.zzy.mall.model.PmsProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: mall
 * @description: 商品分类管理新建分类参数
 * @author: zzy
 * @create: 2024-12-22
 */
@Data
public class PmsProductCategoryParam {

    @ApiModelProperty("上机分类的编号：0表示一级分类")
    private Long parentId;

    private String name;

    private String productUnit;

    @ApiModelProperty("是否显示在导航栏：0->不显示；1->显示")
    private Integer navStatus;

    @ApiModelProperty("显示状态：0->不显示；1->显示")
    private Integer showStatus;

    private Integer sort;

    @ApiModelProperty("图标")
    private String icon;

    private String keywords;

    @ApiModelProperty("描述")
    private String description;

    //TODO 添加list，放置相关属性，用于后续筛选工作
    @ApiModelProperty("用于筛选的相关属性")
    private List<PmsProductAttribute> productAttributeList;

}