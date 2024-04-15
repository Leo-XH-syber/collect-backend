package com.example.backendcollect.enums.types;

import com.example.backendcollect.enums.errorcode.ResponseCode;

/**
 * 工人接受的任务的状态
 */

public enum OrderState implements ResponseCode {
    IN_PROCESS(0, "正在进行中"),
    FINISHED(1, "已经完成"),
    EXPIRED(2, "已经过期"),
    ;
    private final Integer code;
    private final String msg;

    OrderState(Integer code, String msg) {
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
