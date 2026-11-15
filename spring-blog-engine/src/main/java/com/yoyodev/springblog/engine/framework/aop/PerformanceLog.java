package com.yoyodev.springblog.engine.framework.aop;

import com.yoyodev.springblog.common.enumerations.PerformanceLogType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PerformanceLog {
    PerformanceLogType[] logType();
}
