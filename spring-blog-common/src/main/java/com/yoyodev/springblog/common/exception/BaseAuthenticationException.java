package com.yoyodev.springblog.common.exception;


import com.yoyodev.springblog.common.enumerations.ErrorCode;

public class BaseAuthenticationException extends BaseException {
    public BaseAuthenticationException(String message) {
        super(message);
    }

    public BaseAuthenticationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
