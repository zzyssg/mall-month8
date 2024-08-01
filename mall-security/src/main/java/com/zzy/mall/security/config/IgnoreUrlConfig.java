package com.zzy.mall.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mall
 * @description: 白名单配置类
 * @author: zzy
 * @create: 2024-07-27
 */
@ConfigurationProperties(prefix = "security.ignore")
@Data
public class IgnoreUrlConfig {

    private List<String> urls = new ArrayList<>();

}