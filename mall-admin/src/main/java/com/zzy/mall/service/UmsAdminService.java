package com.zzy.mall.service;

import com.zzy.mall.dto.UmsAdminParam;
import com.zzy.mall.dto.UmsAdminUpdateParam;
import com.zzy.mall.dto.UpdatePasswordParam;
import com.zzy.mall.model.UmsAdmin;
import com.zzy.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @program: mall
 * @description: 用户后台管理模块接口
 * @author: zzy
 * @create: 2024-07-27
 */
public interface UmsAdminService {
    UmsAdmin getAdmin(Long id);

    int deleteById(Long id);

    List<UmsAdmin> list(String name, Integer pageNum, Integer pageSize);

    String login(String username, String password);

    UmsAdmin register(UmsAdminParam adminParam);

    List<UmsRole> getRoleByAdminId(Long adminId);

    int update(Long id, UmsAdmin admin);

    int updatePassword(UpdatePasswordParam updatePasswordParam);

    int updateStatus(List<Long> ids, Integer status);

    UmsAdmin getAdminByUsername(String name);

    String refreshToken(String name);

    int updateRoles(Long adminId, List<Long> roleIds);

    UserDetails loadUserByUsername(String username);

    UmsAdminCacheService getAdminCacheService();
}
