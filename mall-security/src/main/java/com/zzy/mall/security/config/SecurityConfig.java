package com.zzy.mall.security.config;

import com.zzy.mall.security.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program: mall
 * @description: security配置类，提供filterChain
 * @author: zzy
 * @create: 2024-07-27
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private IgnoreUrlConfig ignoreUrlConfig;

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private RestAuthenticationFilter authenticationFilter;

    @Autowired
    private JwtAuthenticationTokenFilter jwtTokenFilter;

    @Autowired(required = false)
    private DynamicSecurityService dynamicSecurityService;
    @Autowired(required = false)
    private DynamicSecurityFilter dynamicSecurityFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = httpSecurity.authorizeRequests();
        //对OPTIONS请求放行
        urlRegistry.antMatchers(HttpMethod.OPTIONS).permitAll();
        //对白名单放行
        for (String url: ignoreUrlConfig.getUrls()){
            urlRegistry.antMatchers(url).permitAll();
        }
        //其余正常校验
        urlRegistry.and().authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationFilter)
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //动态权限校验
        if (dynamicSecurityService != null){
            urlRegistry.and().addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
        }
        return httpSecurity.build();
    }

}