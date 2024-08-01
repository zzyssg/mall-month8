package com.zzy.mall.common.exception;

import com.zzy.mall.common.api.IErrorCode;
import com.zzy.mall.common.api.ResultCode;

/**
 * @program: mall
 * @description: 自定义运行时异常
 * @author: zzy
 * @create: 2024-07-21
 */
public class ApiException extends RuntimeException{

    private IErrorCode errorCode;

    //多种构造方法
    public ApiException(IErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message){
        super(message);
    }


    public ApiException(Throwable throwable){
        super(throwable);
    }

    public ApiException(String message,Throwable throwable){
        super(message,throwable);
    }

    public IErrorCode getIErrorCode(){
        return this.errorCode;
    }



}