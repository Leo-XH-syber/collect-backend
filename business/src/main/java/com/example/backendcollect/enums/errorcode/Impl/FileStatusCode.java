package com.example.backendcollect.enums.errorcode.Impl;

import com.example.backendcollect.enums.errorcode.ResponseCode;


/**
 * 文件上传返回给前端的状态
 */
public enum FileStatusCode implements ResponseCode {
    FAIL_TO_UPLOAD(201, "上传失败"),
    FILE_IS_EMPTY(202, "文件是空的");

    private final Integer code;
    private final String msg;

    FileStatusCode(Integer code, String msg) {
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
