package com.example.backendcollect.enums.errorcode.Impl;

import com.example.backendcollect.enums.errorcode.ResponseCode;

/**
 * @author: Rikka
 * @date: 2022/2/27 上午2:51
 * @description
 */
public enum TaskStatusCode implements ResponseCode {
    HE_DIDNT_SELECT_IT(101, "该用户未选择该任务"),
    HE_HAS_SELECTED_IT(102, "该用户已经选择过该任务"),
    HAS_NO_THIS_TASK(103, "没有该任务"),
    ORDER_STATUS_CANNOT_CHANGE(104, "订单状态已不能修改"),
    TASK_TIME_OVER(105, "任务已过期"),
    TASK_TIME_INVALID(106, "任务时间不允许");

    TaskStatusCode(Integer code, String msg) {
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
