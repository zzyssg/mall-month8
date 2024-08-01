package com.zzy.mall.security.config;

import com.zzy.mall.security.component.*;
import com.zzy.mall.security.utils.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: mall
 * @description: securityBean生成类,统一生成Bean，防止循环依赖
 * @author: zzy
 * @create: 2024-07-27
 */
@Configuration
public class CommonSecurityConfig {

    @Bean
    public IgnoreUrlConfig ignoreUrlConfig(){
        return new IgnoreUrlConfig();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil(){
        return new JwtTokenUtil();
    }

    //普通token鉴权
    @Bean
    public RestAuthenticationFilter restAuthenticationFilter(){
        return new RestAuthenticationFilter();
    }

    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler(){
        return new RestAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    //动态权限校验
    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicSecurityMetaDataSource dynamicSecurityMetaDataSource(){
        return new DynamicSecurityMetaDataSource();
    }

    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicSecurityDecisionManager dynamicSecurityDecisionManager(){
        return new DynamicSecurityDecisionManager();
    }
    @Bean
    @ConditionalOnBean(name = "dynamicSecurityService")
    public DynamicSecurityFilter dynamicSecurityFilter(){
        return new DynamicSecurityFilter();
    }



}