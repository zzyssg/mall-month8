package com.zzy.mall.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @program: mall
 * @description: 自定义错误注解：传入的参数可以空或者在指定的范围里。
 * @author: zzy
 * @create: 2024-07-27
 */
@Documented
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FlagValidatorClass.class)
public @interface FlagValidator {

    String[] value() default {};
    String message() default "flag is not found";
    Class<?>[] group() default {};
    Class<? extends Payload>[] payload() default {};

}
