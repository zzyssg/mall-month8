package com.zzy.mall.controller;

import com.zzy.mall.common.api.CommonPage;
import com.zzy.mall.common.api.CommonResult;
import com.zzy.mall.dto.PmsProductParam;
import com.zzy.mall.dto.PmsProductQueryParam;
import com.zzy.mall.dto.PmsProductResult;
import com.zzy.mall.model.PmsProduct;
import com.zzy.mall.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: mall
 * @description: 商品后台管理
 * @author: zzy
 * @create: 2024-08-06
 */
@RestController
@Api(tags = "PmsProductController")
@Tag(name = "PmsProductController",description = "商品后台管理")
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    private PmsProductService productService;

    @ApiOperation("添加商品")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsProductParam productParam){
        int count = productService.create(productParam);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("新增商品失败!");
        }
    }

    @ApiOperation("查询商品")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult list(PmsProductQueryParam productQueryParam,
                             @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize) {
        List<PmsProduct> productList = productService.list(productQueryParam,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(productList));
    }

    @ApiOperation("根据商品名或者货号模糊搜索")
    @RequestMapping(value = "/simpleList",method = RequestMethod.GET)
    public CommonResult simpleList(@RequestParam(value ="keyword",required = false)String keyword){
        List<PmsProduct> productList = productService.simpleList(keyword);
        return CommonResult.success(productList);
    }

    @ApiOperation("更新商品")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public CommonResult update(@RequestParam("id")Long id,@RequestBody PmsProductParam productParam){
        int count = productService.update(id,productParam);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("更新商品失败！");
        }
    }

    @ApiOperation("批量修改删除状态")
    @RequestMapping(value = "/update/deleteStatus",method = RequestMethod.GET)
    public CommonResult updateStatus(@RequestParam("ids")List<Long> ids,@RequestParam("status")Integer status){
        int count = productService.updateDeleteStatus(ids,status);
        if (count > 0){
            return  CommonResult.success();
        }else {
            return CommonResult.failed("批量修改失败");
        }

    }

    @ApiOperation("批量设为新品")
    @RequestMapping(value = "/update/newStatus",method = RequestMethod.GET)
    public CommonResult updateNewStatus(@RequestParam("ids")List<Long> ids,@RequestParam("status")Integer status){
        int count = productService.updateNewStatus(ids,status);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("批量设为新品失败！");
        }
    }

    @ApiOperation("批量上下架商品")
    @RequestMapping(value = "/update/publishStatus",method = RequestMethod.GET)
    public CommonResult updatePublishStatus(@RequestParam("ids")List<Long> ids,@RequestParam("status")Integer status){
        int count = productService.updatePublishStatus(ids,status);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("批量上下架商品失败！");
        }
    }

    @ApiOperation("批量设为推荐商品")
    @RequestMapping(value = "/update/recommendStatus",method = RequestMethod.GET)
    public CommonResult updateRecommendStatus(@RequestParam("ids")List<Long> ids,@RequestParam("status")Integer status){
        int count = productService.updateRecommendStatus(ids,status);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("批量修改为推荐商品失败！");
        }
    }

    @ApiOperation("批量修改审核状态")
    @RequestMapping(value = "/update/verifyStatus",method = RequestMethod.GET)
    public CommonResult updateVerifyStatus(@RequestParam("ids")List<Long> ids,@RequestParam("status")Integer status){
        int count = productService.updateVerifyStatus(ids,status);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("批量修改审核状态失败！");
        }
    }

    @ApiOperation("根据商品ID获取商品编辑信息")
    @RequestMapping(value = "/updateInfo/{id}",method = RequestMethod.GET)
    public CommonResult updateInfo(@PathVariable Long id){
        PmsProductResult productUpdateInfo = productService.getUpdateInfo(id);
        return CommonResult.success(productUpdateInfo);
    }

}