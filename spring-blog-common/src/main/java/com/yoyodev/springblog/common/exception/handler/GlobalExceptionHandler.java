package com.yoyodev.springblog.common.exception.handler;

import com.yoyodev.springblog.common.enumerations.ErrorCode;
import com.yoyodev.springblog.common.exception.BaseAuthenticationException;
import com.yoyodev.springblog.common.model.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

import static com.yoyodev.springblog.common.constants.Constants.EMPTY_STRING;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    protected String getMessage(String code) {
        return messageSource.getMessage(code, null, getCurrentLocale());
    }

    protected String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, getCurrentLocale());
    }

    protected Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    @ExceptionHandler(BaseAuthenticationException.class)
    public ResponseEntity<Object> handleBaseAuthenticationException(BaseAuthenticationException ex) {
        ResponseWrapper<String> response = new ResponseWrapper<>(
            true, 
            ex.getErrorCode() != null ? ex.getErrorCode() : ErrorCode.AUTH_NOT_AUTHENTICATED, 
            getMessage("error.unauthorized"), 
            EMPTY_STRING
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
