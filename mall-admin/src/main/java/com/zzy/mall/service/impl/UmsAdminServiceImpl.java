package com.zzy.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zzy.mall.bo.AdminUserDetails;
import com.zzy.mall.common.exception.Asserts;
import com.zzy.mall.common.utils.RequestUtil;
import com.zzy.mall.dao.UmsAdminDao;
import com.zzy.mall.dao.UmsAdminRoleRelationDao;
import com.zzy.mall.dto.UmsAdminParam;
import com.zzy.mall.dto.UmsAdminUpdateParam;
import com.zzy.mall.dto.UpdatePasswordParam;
import com.zzy.mall.mapper.UmsAdminLoginLogMapper;
import com.zzy.mall.mapper.UmsAdminMapper;
import com.zzy.mall.mapper.UmsAdminRoleRelationMapper;
import com.zzy.mall.model.*;
import com.zzy.mall.security.utils.JwtTokenUtil;
import com.zzy.mall.security.utils.SpringUtil;
import com.zzy.mall.service.UmsAdminCacheService;
import com.zzy.mall.service.UmsAdminService;
import io.lettuce.core.ExperimentalLettuceCoroutinesApi;
import io.swagger.annotations.Example;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.spi.service.contexts.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: mall
 * @description: 用户后台管理模块实现类
 * @author: zzy
 * @create: 2024-07-27
 */
@Service
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private UmsAdminDao adminDao;

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;

    @Override
    public UmsAdmin getAdmin(Long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteById(Long id) {

        getAdminCacheService().delAdmin(id);
        //删除该admin的资源
        getAdminCacheService().delResourceList(id);
        int count =  adminMapper.deleteByPrimaryKey(id);
        return count;
    }

    @Override
    public List<UmsAdmin> list(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        UmsAdminExample adminExample = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = adminExample.createCriteria();
        if (!StrUtil.isEmpty(name)){
            criteria.andNickNameLike("%" + name + "%");
            adminExample.or().andUsernameLike("%" + name + "%");
        }
        return adminMapper.selectByExample(adminExample);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //根据username获取UserDetails
        try {
            AdminUserDetails userDetails = loadUserByUsername(username);
            //获取userDetails后继续处理，校验username和password
            if (!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.failed("密码错误！");
            }
            if (!userDetails.isEnabled()){
                Asserts.failed("用户已封存！");
            }
            //设置到security上下文环境中
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //插入登录日志
            insertLoginLog(username);
            //生成token
            token = jwtTokenUtil.generateToken(userDetails);
        }catch (Exception e){
            log.error("{}登录失败：{}",username,e.getMessage());
        }
        return token;
    }

    private void insertLoginLog(String username) {
        UmsAdmin admin = getAdminByUsername(username);
        if (admin == null){
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        loginLog.setIp(RequestUtil.getRequestIp(request));
        //插入到loginLog表
        try {
            loginLogMapper.insert(loginLog);
        }catch (Exception e) {
            log.error("用户{}登录插入登录日志表失败:{}", username, e.getMessage());
        }
    }

    @Override
    public UmsAdmin register(UmsAdminParam adminParam) {
        //校验username是否已经存在
        UmsAdminExample adminExample = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(adminParam.getUsername());
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(adminExample);
        if (!CollUtil.isEmpty(umsAdmins)){
            //TODO 应该抛出username已经存在的异常
            return null;
        }
        UmsAdmin admin = new UmsAdmin();
        BeanUtils.copyProperties(adminParam,admin);
        admin.setCreateTime(new Date());
        admin.setStatus(1);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //新增admin
        int count;
        try {
            count = adminMapper.insert(admin);
        }catch (Exception e){
            log.error("register:{username} failed.",admin.getUsername());
            return null;
        }
        if (count > 0){
            return admin;
        }else {
            return null;
        }

    }

    @Override
    public List<UmsRole> getRoleByAdminId(Long adminId) {
        List<UmsRole> roles = adminDao.getRoleByAdminId(adminId);
        return roles;
    }

    @Override
    public int update(Long id, UmsAdmin admin) {
        //检查密码-根据adminId从库中查询rowAdmin,比较密码
        UmsAdmin rowAdmin = adminMapper.selectByPrimaryKey(id);
        //不能使用matches，参数不能为null，使用equals
        //if (passwordEncoder.matches(admin.getPassword(),rowAdmin.getPassword())){
        if (rowAdmin.getPassword().equals(admin.getPassword())){
            //密码相同，不更新
            admin.setPassword(null);
        }else {
            //若密码未填写，则不更新；填写了，更新
            if (StrUtil.isEmpty(admin.getPassword())){
                admin.setPassword(null);
            }else {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        admin.setId(id);
        int count = adminMapper.updateByPrimaryKeySelective(admin);
        //更新前删除缓存中admin信息
        getAdminCacheService().delAdmin(id);
        return count;
    }

    @Override
    public int updatePassword(UpdatePasswordParam param) {
        //依次校验参数合法性、用户是否存在、旧密码是否正确
        if (StrUtil.isEmpty(param.getUsername()) ||StrUtil.isEmpty(param.getOldPassword()) || StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (CollUtil.isEmpty(adminList)){
            //该用户不存在
            return -2;
        }
        UmsAdmin rowAdmin = adminList.get(0);
        if (!passwordEncoder.matches(param.getOldPassword(),rowAdmin.getPassword())){
            //密码错误
            return -3;
        }
        //正常更新
        UmsAdmin admin = new UmsAdmin();
        admin.setId(rowAdmin.getId());
        admin.setPassword(passwordEncoder.encode(param.getNewPassword()));
        //更前删除缓存数据
        getAdminCacheService().delAdmin(admin.getId());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        UmsAdminExample adminExample = new UmsAdminExample();
        adminExample.createCriteria().andIdIn(ids);
        UmsAdmin admin = new UmsAdmin();
        admin.setStatus(status);
        return adminMapper.updateByExampleSelective(admin,adminExample);
    }

    @Override
    public UmsAdmin getAdminByUsername(String name) {
        //首先尝试先从redis中获取
        UmsAdmin cacheAdmin = getAdminCacheService().getAdmin(name);
        if (cacheAdmin != null){
            return cacheAdmin;
        }
        //从数据库中获取，然后设置到缓存中
        UmsAdminExample adminExample = new UmsAdminExample();
        adminExample.createCriteria().andUsernameEqualTo(name);
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(adminExample);
        if (!CollUtil.isEmpty(umsAdmins)){
            UmsAdmin admin = umsAdmins.get(0);
            getAdminCacheService().setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public String refreshToken(String token) {
        //获取此人的token
        return jwtTokenUtil.refreshToken(token);
    }

    @Override
    public int updateRoles(Long adminId, List<Long> roleIds) {
        int count = roleIds == null? 0 : roleIds.size();
        //清除之前的角色，重新插入新的角色  ums_role_resource_relation
        UmsAdminRoleRelationExample adminRoleRelationExample = new UmsAdminRoleRelationExample();
        UmsAdminRoleRelationExample.Criteria criteria = adminRoleRelationExample.createCriteria();
        criteria.andAdminIdEqualTo(adminId);
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        //重新插入
        List<UmsAdminRoleRelation> relations = new ArrayList<>();
        if (!CollUtil.isEmpty(roleIds)){
            for (Long roleId : roleIds){
                UmsAdminRoleRelation umsAdminRoleRelation = new UmsAdminRoleRelation();
                umsAdminRoleRelation.setAdminId(adminId);
                umsAdminRoleRelation.setRoleId(roleId);
                relations.add(umsAdminRoleRelation);
            }
            adminRoleRelationDao.insertBatch(relations);
        }
        getAdminCacheService().delResourceList(adminId);
        return count;
    }

    @Override
    public AdminUserDetails loadUserByUsername(String username) {
        //调用getAdminByUsername()获取UmsAdmin
        UmsAdmin umsAdmin = getAdminByUsername(username);
        if (umsAdmin != null){
            //根据adminId获取权限
            //TODO 修改方法，根据
            List<UmsResource> resources = adminDao.getResourceByAdminId(umsAdmin.getId());
            return new AdminUserDetails(umsAdmin,resources);
        }
        throw new UsernameNotFoundException("用户名不存在!");
    }

    @Override
    public UmsAdminCacheService getAdminCacheService() {
        UmsAdminCacheService adminCacheService =  null;
        try {
            adminCacheService =  SpringUtil.getBean(UmsAdminCacheService.class);
        }catch (Exception e){
            log.error("获取cacheAdminService时出现error：{}",e.getMessage());
        }
        return adminCacheService;
    }
}