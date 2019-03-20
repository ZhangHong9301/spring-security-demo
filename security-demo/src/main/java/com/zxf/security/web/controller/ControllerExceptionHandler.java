package com.zxf.security.web.controller;

import com.zxf.security.exception.UserNotExisException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Mr.ZXF
 * on 2019-03-20 10:06
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExisException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) /*服务器内部错误*/
    public Map<String ,Object> handleUserNotExistException(UserNotExisException ex){
        Map<String ,Object> result = new HashMap<>();
        result.put("id",ex.getId());
        result.put("message",ex.getMessage());
        result.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }
}
