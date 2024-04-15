package com.example.backendcollect.enums.errorcode.Impl;

import com.example.backendcollect.enums.errorcode.ResponseCode;

/**
 * @author: Rikka
 * @date: 2022/2/27 上午3:11
 * @description
 */
public enum UserStatusCode implements ResponseCode {
    EMAIL_HAS_BEEN_USED(301, "邮箱已经被占用"),
    EMAIL_NOT_EXIST(302, "邮箱不存在"),
    INCORRECT_USERNAME_OR_PASSWORD(303, "用户名或者密码不对"),
    TOKEN_INVALID(304, "无效的 token, 请重新登录"),
    INSUFFICIENT_PRIVILEGES(305, "权限不足"),
    WORKER_NOT_EXIST(306,"该众包工人不存在");

    UserStatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;
    private final String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;

    }
}
