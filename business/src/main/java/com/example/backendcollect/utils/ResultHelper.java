package com.example.backendcollect.utils;

import com.example.backendcollect.enums.errorcode.Impl.DefaultStatusCode;
import com.example.backendcollect.enums.errorcode.ResponseCode;
import com.example.backendcollect.vo.ResultVO;
import org.springframework.stereotype.Component;


/**
 * @author: Rikka
 * @date: 2022-02-27 02:27:53
 * @description 工具类, 将数据包装后返回给前端, 不直接 new ResultVO 返回
 */

@Component
public class ResultHelper {
    public <T> ResultVO<T> success(T data) {
        return new ResultVO<>(DefaultStatusCode.REQUEST_SUCCESS, data);
    }

    public <T> ResultVO<T> success() {
        return new ResultVO<>(DefaultStatusCode.REQUEST_SUCCESS);
    }

    public <T> ResultVO<T> fail(ResponseCode errorCode) {
        return new ResultVO<>(errorCode);
    }

    public <T> ResultVO<T> result(ResponseCode errorCode, T data) {
        return new ResultVO<>(errorCode, data);
    }
}
