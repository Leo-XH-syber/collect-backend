package com.example.backendcollect.enums.errorcode.Impl;

import com.example.backendcollect.enums.errorcode.ResponseCode;

/**
 * @author: Rikka
 * @date: 2022/2/27 上午3:04
 * @description
 */
public enum ParamStatusCode implements ResponseCode {
    EMPTY(401, "缺少必填参数"),
    EMPTY_USERNAME(402, "用户名为空"),
    EMPTY_PASSWORD(403, "密码为空"),
    EMPTY_TIMESTAMP(404, "时间戳为空"),
    WRONG_FILE_TYPE(405, "没有  file 字段"),
    NOT_FILE(406, "非文件"),
    UID_VALIDATE_FAILED(407, "没有权限访问该路径"),
    UID_NOT_ALLOWED(408, "这不是你的 id"),
    EMAIL_FORMAT_ERROR(409, "邮件格式错误"),
    PASSWORD_FORMAT_ERROR(410, "密码格式错误"),
    PARAM_TYPE_MISMATCH(411, "参数类型不匹配"),
    EMPTY_EMAIL(412, "邮箱为空");


    private final Integer code;
    private final String msg;

    ParamStatusCode(Integer code, String msg) {
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
