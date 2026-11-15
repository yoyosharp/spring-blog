package com.yoyodev.springblog.core.entities.projection;

import java.sql.Timestamp;

public interface UserAuthProjection {

    Long getUserId();

    String getUsername();

    Integer getUserStatus();

    Timestamp getVerifiedAt();

    String getModuleId();

    String getFunctionId();

    Integer getPermissionLevel();

    String getPermissionName();

    Integer getEnabledFlag();
}
