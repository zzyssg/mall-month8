package com.zzy.mall.security.annotation;

import java.lang.annotation.*;

/**
 * @program: mall
 * @description: 自定义异常注解
 * @author: zzy
 * @create: 2024-07-24
 */
@Documented
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CacheException {



}