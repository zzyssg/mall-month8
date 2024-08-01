package com.zzy.mall.controller;

import cn.hutool.core.collection.CollUtil;
import com.zzy.mall.common.api.CommonPage;
import com.zzy.mall.common.api.CommonResult;
import com.zzy.mall.dao.UmsAdminDao;
import com.zzy.mall.dto.UmsAdminLoginParam;
import com.zzy.mall.dto.UmsAdminParam;
import com.zzy.mall.dto.UpdatePasswordParam;
import com.zzy.mall.model.UmsAdmin;
import com.zzy.mall.model.UmsRole;
import com.zzy.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: mall
 * @description: 后台用户模块
 * @author: zzy
 * @create: 2024-07-27
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "UmsAdminController")
@Tag(name = "UmsAdminController",description = "用户后台管理")
public class UmsAdminController {

    @Autowired
    private UmsAdminService adminService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResult register(@RequestBody UmsAdminParam adminParam){
        UmsAdmin admin = adminService.register(adminParam);
        if (admin != null){
            return CommonResult.success(admin);
        }else {
            return CommonResult.failed("注册失败!");
        }
    }

    @ApiOperation("登录，返回token")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult login(@RequestBody UmsAdminLoginParam loginParam){
        String token = adminService.login(loginParam.getUsername(),loginParam.getPassword());
        if (token != null){
            Map<String, String> map =new HashMap<>();
            map.put("username",loginParam.getUsername());
            map.put("token",token);
            return CommonResult.success(map);
        }else {
            return CommonResult.failed("登录失败!");
        }

    }

    @ApiOperation("刷新token")
    @RequestMapping(value = "/refreshToken",method = RequestMethod.POST)
    public CommonResult refreshToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        token = adminService.refreshToken(token);
        if (token == null){
            return CommonResult.failed("token已过期");
        }
        Map<String,String> map = new HashMap<>();
        map.put("tokenHead",tokenHead);
        map.put("token",token);
        return CommonResult.success(map);

    }

    @ApiOperation("获取当前用户登录信息")
    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    public CommonResult getInfo(Principal principal){
        if(principal == null){
            return CommonResult.unAuthentication();
        }
        //获取用户信息
        String name = principal.getName();
        UmsAdmin  admin = adminService.getAdminByUsername(name);
        Map<String,Object> map = new HashMap<>();
        map.put("icon",admin.getIcon());
        map.put("name",admin.getUsername());
        map.put("loginTime",admin.getLoginTime().toString());
        //获取角色信息
        List<UmsRole> roles = adminService.getRoleByAdminId(admin.getId());
        if (!CollUtil.isEmpty(roles)){
            List<String> roleNames = roles.stream().map(role -> role.getName()).collect(Collectors.toList());
            map.put("roleNames",roleNames);
        }
        return CommonResult.success(map);
    }

    @ApiOperation("登出功能")
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public CommonResult logout(){
        //TODO 操作记录 + 前端消除登录信息
        return CommonResult.success();
    }


    @ApiOperation("根据用户名或者姓名分页获取用户列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult list(@RequestParam(value = "name",required = false) String name,
                             @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
        List<UmsAdmin> admins = adminService.list(name,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(admins));
    }

    @ApiOperation("获取用户指定信息")
    @RequestMapping(value = "/{id}",method= RequestMethod.GET)
    public CommonResult detail(@PathVariable Long id){
        UmsAdmin admin = adminService.getAdmin(id);
        return CommonResult.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin admin){
        int count = adminService.update(id,admin);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("修改失败!");
        }
    }

    @ApiOperation("修改指定用户密码")
    @RequestMapping(value = "/update/password",method = RequestMethod.POST)
    public CommonResult updatePassword(@Validated @RequestBody UpdatePasswordParam updatePasswordParam){
        int count = adminService.updatePassword(updatePasswordParam);
        if (count > 0){
            return CommonResult.success();
        }else if (count == -1){
            return CommonResult.failed("参数不能为空！");
        }else if (count == -2){
            return CommonResult.failed("账号不存在！");
        }else if (count == -3){
            return CommonResult.failed("旧密码错误！");
        }else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public CommonResult delete(@PathVariable("id") Long id){
        int count = adminService.deleteById(id);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("删除失败");
        }
    }

    @ApiOperation("修改账号状态")
    @RequestMapping(value = "/update/status",method = RequestMethod.GET)
    public CommonResult updateStatus(@RequestParam("id")Long id,@RequestParam("status")Integer status){
        UmsAdmin admin = new UmsAdmin();
        admin.setId(id);
        admin.setStatus(status);
        int count = adminService.update(id,admin);
        if (count > 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("修改账号状态失败!");
        }
    }

    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update",method = RequestMethod.POST)
    public CommonResult allocateRole(@RequestParam("adminId")Long adminId,
                                     @RequestParam("roleIds")List<Long> roleIds){
        //设置角色
        int count = adminService.updateRoles(adminId,roleIds);
        if (count >= 0){
            return CommonResult.success();
        }else {
            return CommonResult.failed("分配角色失败!");
        }
    }



    @ApiOperation("获取用户指定角色")
    @RequestMapping(value = "/role/{adminId}",method = RequestMethod.GET)
    public CommonResult getRole(@PathVariable("adminId")Long adminId){
        List<UmsRole> roles = adminService.getRoleByAdminId(adminId);
        return CommonResult.success(CommonPage.restPage(roles));
    }





}