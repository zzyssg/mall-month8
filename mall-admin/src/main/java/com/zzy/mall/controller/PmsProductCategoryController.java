package com.zzy.mall.controller;

import com.zzy.mall.common.api.CommonResult;
import com.zzy.mall.dto.PmsProductCategoryParam;
import com.zzy.mall.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: mall
 * @description: 商品分类管理
 * @author: zzy
 * @create: 2024-12-22
 */
@RestController
@Api(value = "PmsProductCategoryController")
@Tag(name = "PmsProductCategoryController",description = "商品分类管理")
@RequestMapping("/productCategory")
public class PmsProductCategoryController {

    @Autowired
    PmsProductCategoryService productCategoryService;

    @ApiOperation("添加商品分类")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsProductCategoryParam productCategoryParam){
        int count = productCategoryService.create(productCategoryParam);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("添加商品分类失败!");
        }
    }

    @ApiOperation("根据ID获取商品分类")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonResult getItem(@PathVariable Long id){
        return null;
    }


    @ApiOperation("删除商品分类")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public CommonResult delete(@PathVariable Long id){
        return null;
    }

    @ApiOperation("分页查询商品分类")
    @RequestMapping(value = "/list/{parentId}",method = RequestMethod.GET)
    public CommonResult listByParentId(@PathVariable Long parentId){
        return null;
    }

    @ApiOperation("查询所有一级分类及子分类")
    @RequestMapping(value = "/list/withChildren",method = RequestMethod.GET)
    public CommonResult listWithChildren(){
        return null;
    }

    @ApiOperation("修改商品分类")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id){
        return null;
    }

    @ApiOperation("修改导航栏状态")
    @RequestMapping(value = "/update/navStatus",method = RequestMethod.GET)
    public CommonResult updateNavStatus(@RequestParam("ids")List<Long> ids,Integer navStatus){
        return null;
    }

    @ApiOperation("修改显示状态")
    @RequestMapping(value = "/update/showStatus",method = RequestMethod.GET)
    public CommonResult updateShowStatus(@RequestParam("ids")List<Long> ids,Integer showStatus){
        return null;
    }



}