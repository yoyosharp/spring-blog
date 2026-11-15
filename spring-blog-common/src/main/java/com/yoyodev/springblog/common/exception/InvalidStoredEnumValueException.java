package com.yoyodev.springblog.common.exception;

import com.yoyodev.springblog.common.enumerations.ErrorCode;

public class InvalidStoredEnumValueException extends BaseException {
    public InvalidStoredEnumValueException() {
        super(ErrorCode.INVALID_DATABASE_VALUE);
    }

    public InvalidStoredEnumValueException(String message) {
        super(ErrorCode.INVALID_DATABASE_VALUE, message);
    }
}
