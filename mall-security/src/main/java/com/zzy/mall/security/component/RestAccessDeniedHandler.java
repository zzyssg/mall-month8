package com.zzy.mall.security.component;

import cn.hutool.json.JSONUtil;
import com.zzy.mall.common.api.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: mall
 * @description: 无权限处理类
 * @author: zzy
 * @create: 2024-07-25
 */
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonResult.forbidden(accessDeniedException.getMessage())));
        response.getWriter().flush();

    }
}