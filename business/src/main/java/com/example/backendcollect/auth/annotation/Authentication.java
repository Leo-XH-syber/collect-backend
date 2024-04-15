package com.example.backendcollect.auth.annotation;

import com.example.backendcollect.enums.types.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Rikka
 * @date 2022/3/2 上午12:52
 * @description 自定义注解, 加上这个注解的 api 需要鉴权(登录)
 */

/**
 * Target 规定了注解加到哪里, TYPE 是类,接口, 或者枚举
 * Retention 规定了注解作用的阶段 源代码, class, runtime
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentication {
    boolean required() default true;

    UserRole[] allowedRoles() default {UserRole.ADMIN, UserRole.EMPLOYER, UserRole.WORKER}; // -1 允许所有人 剩下的与 UserRole 对应
}
