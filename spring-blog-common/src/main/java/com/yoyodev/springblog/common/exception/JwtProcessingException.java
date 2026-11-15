package com.yoyodev.springblog.common.exception;

import com.yoyodev.springblog.common.enumerations.ErrorCode;

public class JwtProcessingException extends BaseException {
    public JwtProcessingException(String message) {
        super(ErrorCode.AUTH_JWT_PROCESSING_ERROR, message);
    }
}
