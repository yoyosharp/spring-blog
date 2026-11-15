package com.yoyodev.springblog.common.exception;

import com.yoyodev.springblog.common.enumerations.ErrorCode;

public class DatabaseOperationException extends BaseException {
    public DatabaseOperationException() {
        super(ErrorCode.PERSISTENCE_ERROR);
    }
}
