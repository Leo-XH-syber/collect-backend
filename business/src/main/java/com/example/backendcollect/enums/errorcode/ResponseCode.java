package com.example.backendcollect.enums.errorcode;

import com.example.backendcollect.enums.BaseEnum;


/**
 * 这是返回给前端的状态吗枚举类接口
 */
public interface ResponseCode extends BaseEnum {
    /**
     * @return 得到错误码
     */
    Integer getCode();

    /**
     * @return 得到信息
     */
    String getMsg();
}
