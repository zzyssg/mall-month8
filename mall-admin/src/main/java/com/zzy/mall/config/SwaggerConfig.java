package com.zzy.mall.config;

import com.zzy.mall.common.config.SwaggerBaseConfig;
import com.zzy.mall.common.domain.SwaggerProperty;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: mall
 * @description: admin模块的swaggerConfig
 * @author: zzy
 * @create: 2024-07-28
 */
@EnableWebMvc
@Configuration
@EnableSwagger2
public class SwaggerConfig extends SwaggerBaseConfig {
    @Override
    public SwaggerProperty swaggerProperties() {
        SwaggerProperty swaggerProperty = new SwaggerProperty();
        swaggerProperty.setAddress("www.baidu.com");
        swaggerProperty.setDescription("simple admin module");
        swaggerProperty.setName("mall-admin");
        swaggerProperty.setEmail("2726066523@qq.com");
        swaggerProperty.setTitle("mall-admin");
        swaggerProperty.setEnableAuth(true);
        swaggerProperty.setPackageUrl("com.zzy.mall.controller");
        swaggerProperty.setVersion("1.0.0");
        return swaggerProperty;
    }
    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return generateBeanPostProcessor();
    }

}