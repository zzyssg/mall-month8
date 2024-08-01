package com.zzy.mall.common.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.zzy.mall.common.domain.WebLog;
import com.zzy.mall.common.utils.RequestUtil;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: mall
 * @description: 统一日志处理切面
 * @author: zzy
 * @create: 2024-07-22
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut(value = "execution(* com.zzy.mall.controller.*.*(..)) || execution(* com.zzy.mall.*.controller.*.*(..))")
    public void webLog(){

    }

    @Before(value = "webLog()")
    public void doBefore(){
        log.info("do before");
    }

    @AfterReturning(value = "webLog()",returning="res")
    public Object doAfterReturning(Object res){
        log.info("res:",res);
        return res;
    }

    @Around(value = "webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //构建webLog对象
        WebLog webLog = new WebLog();
        Object res = joinPoint.proceed();
        //01-ip
        webLog.setIp(RequestUtil.getRequestIp(request));
        //02-description
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)){
            webLog.setDescription(method.getAnnotation(ApiOperation.class).value());
        }
        //03-remoteUser
        webLog.setUsername(request.getRemoteUser());
        //04-startTime
        webLog.setStartTime(startTime);
        //06-basePath - StrUtil + URLUtil  basePath的源从哪里来的？
        String urlString = request.getRequestURL().toString();
        String basePath = StrUtil.removeSuffix(urlString, URLUtil.url(urlString).getPath());
        webLog.setBasePath(basePath);
        //07-uri
        webLog.setUri(request.getRequestURI());
        //08-url
        webLog.setUrl(request.getRequestURL().toString());
        //09-method
        webLog.setMethod(request.getMethod());
        //10-parameter  getRequestParameter的参数分别是什么
        webLog.setParameter(getRequestParameter(method,joinPoint.getArgs()));
        //11-result
        webLog.setResult(res);

        //05-spendTime
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        webLog.setSpendTime(spendTime);
        log.info("登录信息：{}",webLog);
        //返回结果
        return res;


    }

    private Object getRequestParameter(Method request, Object[] args) {
        Parameter[] parameters = request.getParameters();
        List<Object> res  = new ArrayList<>();
        for (int i = 0;i < parameters.length;i++){
            Parameter parameter = parameters[i];
            ResponseBody responseBody = parameter.getAnnotation(ResponseBody.class);
            if (responseBody != null){
                res.add(responseBody);
            }
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);

            if (requestParam != null){
                Map<String,Object> map = new HashMap<>();
                String key = requestParam.name();
                if (StrUtil.isEmpty(key)){
                    key = requestParam.value();
                }
                if (args[i] != null){
                    map.put(key,args[i]);
                    res.add(map);
                }
            }
        }
        if (res.size() == 0){
            return null;
        }else if (res.size() == 1){
            return res.get(0);
        }else {
            return res;
        }
    }


}