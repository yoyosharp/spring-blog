package com.yoyodev.springblog.core.entities.projection;

import java.sql.Timestamp;

public interface UserProjection {
    Long getId();

    String getUsername();

    String getPassword();

    Integer getStatus();

    Timestamp getVerifiedAt();

}
