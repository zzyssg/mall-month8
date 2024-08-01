package com.zzy.mall.dto;

import com.zzy.mall.model.UmsAdmin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @program: mall
 * @description: 不含id的UmsAdmin参数
 * @author: zzy
 * @create: 2024-07-29
 */
@Data
public class UmsAdminParam {
    private String username;

    private String password;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("备注信息")
    private String note;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后登录时间")
    private Date loginTime;

    @ApiModelProperty("帐号启用状态：0->禁用；1->启用")
    private Integer status;
}