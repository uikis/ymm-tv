package com.ymm.ymmtvportal.config;

import com.ymm.ymmtvcommon.exception.YmmException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//拦截自定义异常，以json数据返回错误信息
@ControllerAdvice
public class HandException {

    //如果使用了responseEntity之后就可以不使用responsebody
    @ExceptionHandler(YmmException.class)
    public ResponseEntity<String> handException(YmmException e){
        return ResponseEntity.status(e.getExceptionCode().getCode()).body(e.getExceptionCode().getMsg());
    }
}
