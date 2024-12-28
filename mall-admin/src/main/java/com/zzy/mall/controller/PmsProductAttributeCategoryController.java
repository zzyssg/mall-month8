package com.zzy.mall.controller;

import com.zzy.mall.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @program: mall
 * @description: 商品分类（商品属性分类）管理
 * @author: zzy
 * @create: 2024-12-22
 */
@RestController
@Api(tags = "PmsProductAttributeCategoryController")
@Tag(name = "PmsProductAttributeCategoryController",description = "属性分类管理")
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @ApiOperation("获取单个商品属性分类的信息")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonResult getProductAttributeCategoryInfo(@PathVariable Long id){
        return null;
    }

    @ApiOperation("添加商品属性分类")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create( ){
        return null;
    }

    @ApiOperation("删除单个商品属性分类")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public CommonResult delete(@PathVariable Long id){
        return  null;
    }

    @ApiOperation("分页获取所有商品属性分类")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult list(){
        return null;
    }

    @ApiOperation("获取所有属性分类及其下属性")
    @RequestMapping(value = "/list/withAttr",method = RequestMethod.GET)
    public CommonResult listWithAttr(){
        return null;
    }

    @ApiOperation("修改商品属性分类")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id){
        return null;
    }


}