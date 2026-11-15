package com.yoyodev.springblog.common.framework.security;

import com.yoyodev.springblog.common.model.UserPrincipal;

/**
 * Port interface for loading a {@link UserPrincipal} by username.
 *
 * <p>Implemented in the engine by {@code AuthenticationService}. Exists so
 * {@code JwtFilter} (engine) can depend only on this narrow port instead of
 * the whole {@code AuthenticationService}, breaking the
 * JwtFilter -&gt; AuthenticationService cycle.
 */
public interface UserPrincipalProvider {

    UserPrincipal getUserPrincipalByUsername(String username);
}
