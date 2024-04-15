package com.example.backendcollect.enums.types;

import com.example.backendcollect.enums.BaseEnum;

/**
 * 发布任务的状态
 */

public enum TaskState implements BaseEnum {
    IN_PROCESS(0), // 仍可以选
    PERSON_FULL(1), // 不可以选, 尚未过期
    TIME_OVER(2), // 已经过期
    ;

    TaskState(Integer code) {
        this.code = code;
    }

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
