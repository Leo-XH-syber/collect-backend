package com.example.backendcollect.enums.types;

import com.example.backendcollect.enums.BaseEnum;

public enum ReportBugType implements BaseEnum {
    DEFAULT(0),
    BUG_REPORT(1),
    DUPLICATED_BUG(2),
    NOT_BUG(3),
    ;

    ReportBugType(Integer code) {
        this.code = code;
    }

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
