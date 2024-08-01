package com.zzy.mall.security.aspect;

import com.zzy.mall.common.exception.ApiException;
import com.zzy.mall.security.annotation.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @program: mall
 * @description: 缓存切面类，处理因缓存服务器宕机引起的问题
 * @author: zzy
 * @create: 2024-07-24
 */
@Aspect
@Slf4j
@Component
public class RedisAspect {

    @Pointcut(value = "execution(* com.zzy.mall.service.*CacheService.*(..)) || execution(* com.zzy.mall.*.*CacheService.*(..))")
    public void cacheAspect(){

    }

    @Around(value = "cacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //对异常进行处理，如果异常是有CacheException注释，则抛出
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            //TODO 抛出后，不还是没有正常运行？
            if (method.isAnnotationPresent(CacheException.class)){
                throw e;
            }else {
                log.error("cacheError:{}",e.getMessage());
            }
        }
        return result;

    }

}