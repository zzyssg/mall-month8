package com.zzy.mall.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * @program: mall
 * @description: 自定义校验规则
 * @author: zzy
 * @create: 2024-07-27
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator,Integer> {

    private String[] values;

    @Override
    public void initialize(FlagValidator flagValidator) {
        this.values = flagValidator.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = false;
        if (value == null){
            return true;
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(String.valueOf(value))){
                isValid = true;
                break;
            }
        }



        return isValid;
    }
}