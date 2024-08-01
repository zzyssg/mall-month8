package com.zzy.mall.security.component;

import cn.hutool.core.util.StrUtil;
import com.zzy.mall.security.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: mall
 * @description: jwtToken过滤，对token进行处理
 * @author: zzy
 * @create: 2024-07-25
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //从request中获取token，检验token
        String token = request.getHeader(tokenHeader);
        //token不为空，则从token中获取username
        if (!StrUtil.isEmpty(token) && token.startsWith(tokenHead)){
            token = token.substring(tokenHead.length());
            //校验token是否合法
            String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
            if (usernameFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(usernameFromToken);
                if (jwtTokenUtil.validateTokenUsername(token,userDetails)){
                    //设置上下文
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("{} login...",usernameFromToken);
                }
            }
        }
        filterChain.doFilter(request,response);

    }
}