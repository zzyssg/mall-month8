package com.zzy.mall.bo;

import cn.hutool.core.collection.CollUtil;
import com.zzy.mall.model.UmsAdmin;
import com.zzy.mall.model.UmsResource;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mall
 * @description: 自定义UserDetails，包含Admin和Resource类
 * @author: zzy
 * @create: 2024-07-27
 */
public class AdminUserDetails implements UserDetails {

    private final UmsAdmin umsAdmin;

    private final List<UmsResource> resourceList;

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsResource> resourceList) {
        this.umsAdmin = umsAdmin;
        this.resourceList = resourceList;
    }


    //授予的权限：id+":"+resourceName
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return resourceList.stream().map(resource-> new SimpleGrantedAuthority(resource.getId() + ":" +resource.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}