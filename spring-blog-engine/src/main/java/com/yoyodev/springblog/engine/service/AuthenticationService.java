package com.yoyodev.springblog.engine.service;

import com.yoyodev.springblog.common.framework.security.UserPrincipalProvider;
import com.yoyodev.springblog.common.model.UserPrincipal;
import com.yoyodev.springblog.engine.model.AuthUserRequest;
import com.yoyodev.springblog.engine.model.AuthModel;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface AuthenticationService extends UserPrincipalProvider {

    AuthModel login(AuthUserRequest request);

    String getAccessTokenByRefreshToken(AuthModel authModel);

    void blacklistToken(String token);

    /**
     * Process an OAuth2 user, creating or updating the local user as needed.
     *
     * @param provider   The OAuth2 provider (e.g., "google", "github")
     * @param providerId The user ID from the provider
     * @param email      The user's email
     * @param name       The user's name
     * @param attributes Additional attributes from the OAuth2 provider
     * @return The UserPrincipal for the processed user
     */
    UserPrincipal processOAuth2User(String provider, String providerId, String email, String name,
                                    Map<String, Object> attributes);

    /**
     * Generate a JWT token for an OAuth2 authenticated user.
     *
     * @param authentication The authentication object from Spring Security
     * @return An AuthModel containing the JWT token and refresh token
     */
    AuthModel generateTokenForOAuth2User(Authentication authentication);
}
