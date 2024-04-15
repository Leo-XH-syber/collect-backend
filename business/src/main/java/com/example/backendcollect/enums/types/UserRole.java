package com.example.backendcollect.enums.types;

import com.example.backendcollect.enums.BaseEnum;

public enum UserRole implements BaseEnum {
    ADMIN(0),
    WORKER(1),
    EMPLOYER(2),
    ;
    public final Integer code;

    UserRole(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
