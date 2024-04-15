package com.example.backendcollect.exception;

import com.example.backendcollect.enums.errorcode.ResponseCode;

/**
 * @author: Rikka
 * @date: 2022/3/3 下午5:09
 * @description
 */
public class ServiceException extends RuntimeException {
    ResponseCode responseCode;

    public ServiceException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    @Override
    public String getMessage() {
        return responseCode.getMsg();
    }
}
