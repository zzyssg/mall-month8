package com.zzy.mall.common.exception;

import com.zzy.mall.common.api.IErrorCode;

/**
 * @program: mall
 * @description: 自定义断言类，抛出自定义异常
 * @author: zzy
 * @create: 2024-07-21
 */
public class Asserts {

    public static void failed(String message){
        throw new ApiException(message);
    }

    public static void failed(IErrorCode errorCode){
        throw new ApiException(errorCode);
    }



}