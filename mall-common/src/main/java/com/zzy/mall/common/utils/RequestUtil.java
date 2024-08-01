package com.zzy.mall.common.utils;

import cn.hutool.core.util.StrUtil;
import sun.swing.StringUIClientPropertyKey;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @program: mall
 * @description: 请求工具类，包含获取请求的ip
 * @author: zzy
 * @create: 2024-07-21
 */
public class RequestUtil {

    public static String getRequestIp(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if (StrUtil.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)){
            //从本地访问的话，则从本地网卡中获取ip
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)){
                InetAddress inetAddress;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }
        //若经过多个代理转发，ip
        if (!StrUtil.isEmpty(ipAddress) && ipAddress.length() > 15){
            if (ipAddress.indexOf(",")>0){
            ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));

            }
        }
        return ipAddress;

    }


}