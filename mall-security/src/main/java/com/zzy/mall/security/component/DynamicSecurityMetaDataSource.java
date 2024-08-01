package com.zzy.mall.security.component;

import cn.hutool.core.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @program: mall
 * @description: 动态权限规则数据源
 * @author: zzy
 * @create: 2024-07-25
 */
public class DynamicSecurityMetaDataSource implements SecurityMetadataSource {

    private Map<String,ConfigAttribute> configAttributeMap;

    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @PostConstruct
    public void loadDataSource(){
        if (configAttributeMap == null){
            this.configAttributeMap = dynamicSecurityService.loadDataSource();
        }
    }

    public void clearDataSource(){
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取需要访问o所需要的资源，有其中任意一个资源即可
        List<ConfigAttribute> res = new ArrayList<>();
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        String requestURL = request.getRequestURL().toString();
        String path = URLUtil.url(requestURL).getPath();
        //遍历动态权限资源，获取符合要求的资源
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String pathRegex : configAttributeMap.keySet()) {
            if (pathMatcher.match(pathRegex,path)){
                res.add(configAttributeMap.get(pathRegex));
            }
        }
        return res;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}