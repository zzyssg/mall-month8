package com.zzy.mall.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: mall
 * @description: Mybatis配置类，扫描注册dao层接口
 * @author: zzy
 * @create: 2024-07-28
 */
@Configuration
@MapperScan(basePackages = {"com.zzy.mall.dao", "com.zzy.mall.mapper"})
public class MybatisConfig {
}