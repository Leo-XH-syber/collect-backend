package com.example.backendcollect.exception;

import com.example.backendcollect.enums.errorcode.Impl.ParamStatusCode;
import com.example.backendcollect.enums.errorcode.Impl.UserStatusCode;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.ResultVO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolationException;


/**
 * @author: Rikka
 * @date: 2022-02-27 03:20:05
 * @description 全局异常处理
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    ResultHelper resultHelper;

    @Autowired
    public void setResultHelper(ResultHelper resultHelper) {
        this.resultHelper = resultHelper;
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public ResultVO<Object> MultipartExceptionHandler(MultipartException e) {
        return resultHelper.fail(ParamStatusCode.WRONG_FILE_TYPE);
    }


    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    public ResultVO<Object> MissingServletRequestPartException(MissingServletRequestPartException e) {
        return resultHelper.fail(ParamStatusCode.NOT_FILE);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResultVO<Object> IllegalArgumentException(IllegalArgumentException e) {
        return resultHelper.fail(ParamStatusCode.PARAM_TYPE_MISMATCH);
    }

    @ExceptionHandler({SignatureException.class, MalformedJwtException.class, ExpiredJwtException.class})
    @ResponseBody
    public ResultVO<Object> SignatureException(Exception e) {
        return resultHelper.fail(UserStatusCode.TOKEN_INVALID);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResultVO<Object> ServiceException(ServiceException e) {
        return resultHelper.fail(e.getResponseCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResultVO<Object> MissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return resultHelper.fail(ParamStatusCode.EMPTY);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResultVO<Object> ConstraintViolationException(ConstraintViolationException e) {
        return resultHelper.fail(ParamStatusCode.valueOf(e.getMessage().split(": ")[1]));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResultVO<Object> MethodArgumentNotValidException(BindException e) {
        String msg = (e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return resultHelper.fail(ParamStatusCode.valueOf(msg));
    }
}
