package com.zzy.mall.security.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @program: mall
 * @description: SpringBean的获取工具类，通过BeanName，ClassName获取，关键字段为ApplicationContext
 * @author: zzy
 * @create: 2024-07-25
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null){
            SpringUtil.applicationContext = applicationContext;
        }
    }

    public static Object getBean(String beanName){
        return getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
    public static <T> T getBean(String beanName,Class<T> clazz){
        return getApplicationContext().getBean(beanName,clazz);
    }

}