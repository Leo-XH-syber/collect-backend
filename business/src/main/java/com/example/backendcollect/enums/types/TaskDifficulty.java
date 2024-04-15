package com.example.backendcollect.enums.types;

import com.example.backendcollect.enums.BaseEnum;

public enum TaskDifficulty implements BaseEnum {
    EASY(0),
    MEDIUM(1),
    HARD(2),
    ;

    TaskDifficulty(Integer code) {
        this.code = code;
    }

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}