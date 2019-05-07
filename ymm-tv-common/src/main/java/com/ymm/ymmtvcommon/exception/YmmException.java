package com.ymm.ymmtvcommon.exception;

import com.ymm.ymmtvcommon.excetionEnum.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YmmException extends RuntimeException{

    private ExceptionCode exceptionCode;
}
