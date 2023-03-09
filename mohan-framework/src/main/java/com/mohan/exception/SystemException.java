package com.mohan.exception;


import com.mohan.enums.AppHttpCodeEnum;
import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class SystemException extends RuntimeException{

    private int code;
    private String msg;

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
