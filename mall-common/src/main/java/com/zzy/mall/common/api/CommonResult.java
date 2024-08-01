package com.zzy.mall.common.api;

import lombok.Data;

import javax.xml.transform.Result;

/**
 * @program: mall
 * @description: 统一返回结果封装类
 * @author: zzy
 * @create: 2024-07-20
 */
@Data
public class CommonResult<T> {

    private String message;

    private Integer code;

    private T data;

    public  CommonResult(String message, Integer code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    //success
    public static <T> CommonResult<T>  success() {
        return new CommonResult<T>(ResultCode.SUCCESS.getMessage(),ResultCode.SUCCESS.getCode(),null);
    }

    public static <T> CommonResult<T> success(T data){
        return new CommonResult<>(ResultCode.SUCCESS.getMessage(), ResultCode.SUCCESS.getCode(), data);
    }

    //failed
    public static <T> CommonResult<T> failed(){
        return new CommonResult<>(ResultCode.FAILED.getMessage(), ResultCode.FAILED.getCode(), null);
    }

    public static <T> CommonResult<T> failed(String message){
        return new CommonResult<>(message,ResultCode.FAILED.getCode(), null);
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode){
        return new CommonResult<>(errorCode.getMessage(),errorCode.getCode(),null);
    }

    //验证失败
    public static <T> CommonResult<T> validateFailed(){
        return new CommonResult<>(ResultCode.VALIDATED_FAILED.getMessage(), ResultCode.VALIDATED_FAILED.getCode(), null);
    }

    public static <T> CommonResult<T> validatedFailed(String message){
        return new CommonResult<>(message,ResultCode.VALIDATED_FAILED.getCode(), null);
    }

    //未授权
    public static <T> CommonResult<T> unAuthentication(){
        return new CommonResult<>(ResultCode.UN_AUTHENTICATION.getMessage(), ResultCode.UN_AUTHENTICATION.getCode(), null);
    }
    public static <T> CommonResult<T> unAuthentication(String message){
        return new CommonResult<>(message,ResultCode.UN_AUTHENTICATION.getCode(), null);
    }

    //权限不足
    public static <T> CommonResult<T> forbidden(){
        return new CommonResult<>(ResultCode.FORBIDDEN.getMessage(), ResultCode.FORBIDDEN.getCode(), null);
    }
    public static <T> CommonResult<T> forbidden(String message){
        return new CommonResult<>(message, ResultCode.FAILED.getCode(), null);
    }


}