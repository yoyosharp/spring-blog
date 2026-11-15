package com.yoyodev.springblog.common.framework.security;

import com.yoyodev.springblog.common.enumerations.EnabledStatus;
import com.yoyodev.springblog.common.enumerations.PermissionLevel;
import com.yoyodev.springblog.common.model.SimplePermission;
import org.springframework.security.core.GrantedAuthority;

public class GrantedPermission implements GrantedAuthority {
    private final SimplePermission permission;

    public GrantedPermission(SimplePermission permission) {
        assert permission != null;
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission.moduleId() + ":" + permission.functionId();
    }


    public String getModule() {
        return permission.moduleId();
    }

    public String getFunctionName() {
        return permission.functionId();
    }

    public PermissionLevel getLevel() {
        return permission.level();
    }

    public boolean hasPrivilege(PermissionLevel targetLevel) {
        return permission.level().getValue() >= targetLevel.getValue();
    }

    public boolean isEnabled() {
        return permission.isEnabled() == EnabledStatus.Enabled;
    }
}
