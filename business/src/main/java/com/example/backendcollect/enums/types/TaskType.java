package com.example.backendcollect.enums.types;

import com.example.backendcollect.enums.BaseEnum;

/**
 * 包括：
 * 性能测试、功能测试、安全性测试、用户体验测试、兼容性测试、探索性测试
 */

public enum TaskType implements BaseEnum {
    FUNCTIONAL_TEST(0),
    PERFORMANCE_TEST(1),
    SAFETY_TEST(2),
    EXPERIENCE_TEST(3),
    COMPATIBILITY_TEST(4),
    EXPLORATION_TEST(5),
    ;

    TaskType(Integer code) {
        this.code = code;
    }

    private final Integer code;

    @Override
    public Integer getCode() {
        return code;
    }

}
