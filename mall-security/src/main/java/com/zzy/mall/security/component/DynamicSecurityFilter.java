package com.zzy.mall.security.component;

import cn.hutool.core.text.AntPathMatcher;
import com.zzy.mall.security.config.IgnoreUrlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @program: mall
 * @description: 动态权限过滤器，实现基于路径的动态权限过滤
 * @author: zzy
 * @create: 2024-07-27
 */
@Slf4j
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {


    @Autowired
    private DynamicSecurityMetaDataSource dynamicSecurityMetaDataSource;

    @Autowired
    private IgnoreUrlConfig ignoreUrlConfig;

    @Autowired
    public void setDynamicSecurityDecisionManager(DynamicSecurityDecisionManager dynamicSecurityDecisionManager){
        super.setAccessDecisionManager(dynamicSecurityDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //放行OPTIONS请求
        if (httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.toString())){
            fi.getChain().doFilter(fi.getRequest(),fi.getResponse());
        }
        //放行白名单
        String path = httpServletRequest.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String pathRegex : ignoreUrlConfig.getUrls()){
            if (antPathMatcher.match(pathRegex,path)){
                fi.getChain().doFilter(fi.getRequest(),fi.getResponse());
                return;
            }
        }
        //正常过滤
        InterceptorStatusToken interceptorStatusToken = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(),fi.getResponse());
        }finally {
            super.afterInvocation(interceptorStatusToken,fi);
        }

    }

    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetaDataSource;
    }
}