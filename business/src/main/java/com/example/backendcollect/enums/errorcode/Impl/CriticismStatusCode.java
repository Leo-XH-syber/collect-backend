package com.example.backendcollect.enums.errorcode.Impl;

import com.example.backendcollect.enums.errorcode.ResponseCode;


public enum CriticismStatusCode implements ResponseCode {
    CRITICISM_NOT_EXISTS(501, "评价不存在");
    private final Integer code;
    private final String msg;

    CriticismStatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
