package com.example.backendcollect.vo;

import com.example.backendcollect.enums.errorcode.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
    /*返回状态码*/
    private Integer code;
    /*携带数据*/
    private T data = null;
    /*返回信息*/
    private String msg;

    public ResultVO(Integer code) {
        this.code = code;
    }

    public ResultVO(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(ResponseCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public ResultVO(ResponseCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.data = data;
    }

}
