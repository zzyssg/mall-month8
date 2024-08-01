package com.zzy.mall.security.component;

import cn.hutool.core.collection.CollUtil;
import com.zzy.mall.common.api.ResultCode;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * @program: mall
 * @description: 动态权限决策期，判定用户是否拥有权限
 * @author: zzy
 * @create: 2024-07-27
 */
public class DynamicSecurityDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (CollUtil.isEmpty(configAttributes)){
            return;
        }
        //如果拥有的权限和需要的权限匹配，则放行。
        Iterator<? extends GrantedAuthority> hasAuthorities = authentication.getAuthorities().iterator();
        while (hasAuthorities.hasNext()){
            GrantedAuthority hasAuthority = hasAuthorities.next();
            for (ConfigAttribute needAuthority : configAttributes){
                if (hasAuthority.getAuthority().trim().equals(needAuthority.getAttribute().trim())){
                    return;
                }
            }
        }
        throw new AccessDeniedException(ResultCode.FORBIDDEN.getMessage());

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}