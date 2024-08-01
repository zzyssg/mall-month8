package com.zzy.mall.common.domain;

import lombok.Data;

/**
 * @program: mall
 * @description: 统一日志记录对象
 * @author: zzy
 * @create: 2024-07-22
 */
@Data
public class WebLog {
    //请求类信息
    private String ip;
    private String username;
    private Long spendTime;
    private Long startTime;
    private String description;

    //方法信息
    private String basePath;
    private String url;
    private String uri;
    private String method;
    private Object parameter;

    //目标放法执行结果
    private Object result;



}