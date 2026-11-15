package com.yoyodev.springblog.common.model;

import com.yoyodev.springblog.common.enumerations.UserStatus;

import java.util.Set;

public record UserPrincipal(long id,
                            String username,
                            UserStatus status,
                            boolean isVerified,
                            Set<SimplePermission> permissions) {
}


