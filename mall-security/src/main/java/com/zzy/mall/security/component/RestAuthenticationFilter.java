package com.zzy.mall.security.component;

import com.zzy.mall.common.api.CommonResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: mall
 * @description: 未授权自定义处理类
 * @author: zzy
 * @create: 2024-07-25
 */
public class RestAuthenticationFilter implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println(CommonResult.unAuthentication(authException.getMessage()));
        response.getWriter().flush();

    }
}