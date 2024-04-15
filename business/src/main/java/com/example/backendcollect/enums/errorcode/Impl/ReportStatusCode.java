package com.example.backendcollect.enums.errorcode.Impl;

import com.example.backendcollect.enums.errorcode.ResponseCode;

/**
 * @author: Rikka
 * @date: 2022/3/7 上午12:12
 * @description
 */
public enum ReportStatusCode implements ResponseCode {
    REPORT_NOT_EXIST(501, "报告不存在");
    private final Integer code;
    private final String msg;

    ReportStatusCode(Integer code, String msg) {
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
