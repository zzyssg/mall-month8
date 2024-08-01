package com.zzy.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: mall
 * @description: 修改密码参数类
 * @author: zzy
 * @create: 2024-07-30
 */
@Data
public class UpdatePasswordParam {

    @NotEmpty
    @ApiModelProperty(value = "账号-用户名",required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "旧密码",required = true)
    private String oldPassword;
    @NotEmpty
    @ApiModelProperty(value = "新密码",required = true)
    private String newPassword;

}