package com.zzy.mall.common.domain;

import lombok.Data;

/**
 * @program: mall
 * @description: swagger属性变量对象
 * @author: zzy
 * @create: 2024-07-20
 */
@Data
public class SwaggerProperty {
    private String version;
    private String title;
    private String description;
    private String name;
    private String email;
    private String address;
    private boolean enableAuth;
    private String packageUrl;


}