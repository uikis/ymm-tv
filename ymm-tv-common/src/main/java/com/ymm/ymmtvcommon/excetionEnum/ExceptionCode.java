package com.ymm.ymmtvcommon.excetionEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常枚举
 * 枚举是没有set值的，但可以通过改造构造方法get
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionCode {
    ANIME_FIND_FAILED("查询番剧失败！", 500),
    ANIME_CANNOT_FIND("未能找到番剧！", 404),
    PARAM_NULL("查询参数为空！", 404),
    USER_NULL("用户参数为空！", 404),
    HANDLE_FALIED("操作失败！", 500),
    USER_EXIST("用户已经存在！", 500),
    USER_ERRO("用户名或者密码错误", 500)
    ;
    private String msg;
    private Integer code;
}
