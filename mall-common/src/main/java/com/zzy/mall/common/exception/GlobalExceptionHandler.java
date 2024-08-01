package com.zzy.mall.common.exception;

import cn.hutool.core.util.StrUtil;
import com.zzy.mall.common.api.CommonResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileDescriptor;
import java.sql.SQLSyntaxErrorException;

/**
 * @program: mall
 * @description: 自定义全局异常处理类
 * @author: zzy
 * @create: 2024-07-21
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handleApiException(ApiException e){
        if (e.getIErrorCode() != null){
            return CommonResult.failed(e.getIErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        String message = "";
        if (bindingResult != null && bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            message = fieldError.getField() + fieldError.getDefaultMessage();
        }
        return CommonResult.validatedFailed(message);
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult handleBindException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        String message = "";
        if (bindingResult != null && bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            message = fieldError.getField()  + fieldError.getDefaultMessage();
        }
        return CommonResult.validatedFailed(message);
    }

    @ResponseBody
    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public CommonResult handleSQLSyntaxErrorException(SQLSyntaxErrorException e){
        String message = e.getMessage();
        if (!StrUtil.isEmpty(message) && message.contains("denied")){
            message = "环境为测试环境，请切换到本地环境或者正式环境";
        }
        return CommonResult.failed(message);
    }

}