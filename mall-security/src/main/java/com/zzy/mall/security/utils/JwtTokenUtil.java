package com.zzy.mall.security.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: mall
 * @description: jwtToken工具类
 * @author: zzy
 * @create: 2024-07-24
 */
@Slf4j
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.secret}")
    private String secret;
    private final String CLAIM_USER = "sub";
    private final String CLAIM_CREATE = "created";

    //使用Map生成token

    //使用username和password生成token
    public String generateToken(UserDetails userDetails){
        //构建Claims
        Map<String,Object> map = new HashMap<>();
        map.put(CLAIM_USER,userDetails.getUsername());
        map.put(CLAIM_CREATE,new Date());
        return generateToken(map);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .expiration(expiration())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    private Date expiration() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    //刷新token
    public String refreshToken(String oldToken){
        //刷新前验证token的合法性
        if (StrUtil.isEmpty(oldToken)){
            return null;
        }
        //从token中去除tokenHead
        oldToken = oldToken.substring(tokenHead.length());
        if (StrUtil.isEmpty(oldToken)){
            return null;
        }
        //是否能从token中获取claims
        Claims claims = getClaimsFromToken(oldToken);
        if (claims == null){
            return null;
        }
        //判断是否过期，过期直接返回null
        if (isExpired(oldToken)){
            return null;
        }
        //如果在最近30分钟刷新过，则不在进行刷新
        if (refreshTokenRecent(oldToken)){
            return oldToken;
        }else {
            //JWT Claims instance is immutable and may not be modified.
            //claims.put(CLAIM_CREATE,new Date());
            //使用username重新生成token
            Map<String,Object> map = new HashMap<>();
            map.put(CLAIM_USER,claims.getSubject());
            map.put(CLAIM_CREATE,new Date());
            return generateToken(map);
        }

    }

    private boolean refreshTokenRecent(String oldToken) {
        //获取token的创建时间
        Claims claimsFromToken = getClaimsFromToken(oldToken);
        if (claimsFromToken == null){
            return false;
        }
        Date createTime = new Date((Long) claimsFromToken.get(CLAIM_CREATE));
        Date cur = new Date();
        //    创建时间<= 当前时间 <=创建时间 + 30分钟
        return createTime.before(cur) && DateUtil.offset(createTime,DateField.SECOND,1*60).after(cur);
    }

    //获取map
    public Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims =  Jwts.parser().setSigningKey(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        }catch (Exception e){
            log.error("获取claims出错：{}",e.getMessage());
            claims = null;
        }
        return claims;
    }

    //从token中获取username
    public String getUsernameFromToken(String token){
        String username;
        try {
            Claims claimsFromToken = getClaimsFromToken(token);
            username =claimsFromToken.getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    //计算token的过期时间
    public Date getExpiration(String token){
        Claims claimsFromToken = getClaimsFromToken(token);
        return claimsFromToken.getExpiration();
    }

    //判断token是否过期
    public boolean isExpired(String token){
        // expiration now
        return getExpiration(token).before(new Date());
    }

    //判断 token和username是否匹配，有可能token生效期间，username无效了
    public boolean validateTokenUsername(String token,UserDetails userDetails){
        //判断username是否有效
        return !isExpired(token) && userDetails.getUsername().equals(getUsernameFromToken(token));

    }



}