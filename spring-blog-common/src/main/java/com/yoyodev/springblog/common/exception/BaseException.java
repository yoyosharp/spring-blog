package com.yoyodev.springblog.common.exception;

import com.yoyodev.springblog.common.enumerations.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    protected final ErrorCode errorCode;

    public BaseException() {
        super();
        this.errorCode = ErrorCode.DEFAULT;
    }

    public BaseException(String message) {
        super(message);
        this.errorCode = ErrorCode.DEFAULT;
    }

    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

}
