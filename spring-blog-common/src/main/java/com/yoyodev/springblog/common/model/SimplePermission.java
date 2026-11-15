package com.yoyodev.springblog.common.model;

import com.yoyodev.springblog.common.enumerations.EnabledStatus;
import com.yoyodev.springblog.common.enumerations.PermissionLevel;

public record SimplePermission(String name,
                               String moduleId,
                               String functionId,
                               PermissionLevel level,
                               EnabledStatus isEnabled) {
    public String getEffectiveName() {
        return moduleId + ":" + functionId + ":" + level + ":" + isEnabled;
    }
}
