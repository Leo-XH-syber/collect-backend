package com.example.backendcollect.enums.types;

import com.example.backendcollect.enums.BaseEnum;

public enum RecStrategyType implements BaseEnum {

    HISTORY_POPULAR(0),
    MONTH_POPULAR(1),
    ALS(2),
    REAL_TIME(3),
    ;


    RecStrategyType(Integer code) {
        this.code = code;
    }

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
