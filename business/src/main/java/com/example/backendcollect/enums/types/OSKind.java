package com.example.backendcollect.enums.types;

import com.example.backendcollect.enums.BaseEnum;

public enum OSKind implements BaseEnum {
    Android(0),
    HarmonyOS(1),
    iOS(2),
    iPadOS(3),
    macOS(4),
    Windows(5),
    Linux(6),
    Unix(7),
    Others(8);

    OSKind(Integer code) {
        this.code = code;
    }

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }
}
