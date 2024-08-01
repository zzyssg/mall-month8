package com.zzy.mall.common.api;

/**
 * @program: mall
 * @description: 统一返回结果枚举类
 * @author: zzy
 * @create: 2024-07-20
 */
public enum ResultCode implements IErrorCode {

   SUCCESS(200,"success"),
    FAILED(300,"failed"),
    VALIDATED_FAILED(401,"validated_failed"),
    UN_AUTHENTICATION(402,"authenticate_failed"),
    FORBIDDEN(403,"forbidden")
    ;

   private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
