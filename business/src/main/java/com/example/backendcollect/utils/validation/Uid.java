package com.example.backendcollect.utils.validation;


import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: Rikka
 * @date: 2022/3/4 下午4:04
 * @description 这个注解是用来验证传入的 uid 是否和 token 取出的 uid 一致的
 */

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UidValidtor.class)
public @interface Uid {
    String message() default "UID_VALIDATE_FAILED";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
