package com.example.backendcollect.enums.errorcode.Impl;

import com.example.backendcollect.enums.errorcode.ResponseCode;

/**
 * @author: Rikka
 * @date: 2022/2/27 上午2:32
 * @description
 */
public enum DefaultStatusCode implements ResponseCode {

    REQUEST_SUCCESS(0, "一切 OK"),
    REQUEST_FAIL(1, "服务器有问题");

    DefaultStatusCode(Integer code, String msg) {
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
