package com.yoyodev.springblog.engine.model;

import com.yoyodev.springblog.common.enumerations.UserStatus;
import com.yoyodev.springblog.common.model.SimplePermission;
import com.yoyodev.springblog.common.model.UserPrincipal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String email;
    private UserStatus status;
    private Timestamp verifiedAt;
    private Set<SimplePermission> effectivePermissions;

    public UserResponse(UserPrincipal userPrincipal) {
        this.username = userPrincipal.username();
        this.email = null;
        this.status = userPrincipal.status();
        this.verifiedAt = null;
        this.effectivePermissions = userPrincipal.permissions();
    }
}
