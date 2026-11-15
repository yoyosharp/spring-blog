package com.yoyodev.springblog.engine.framework.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.proc.BadJWTException;
import com.yoyodev.springblog.engine.framework.cache.RedisCacheService;
import com.yoyodev.springblog.common.constants.RedisConstants;
import com.yoyodev.springblog.common.enumerations.ErrorCode;
import com.yoyodev.springblog.common.exception.BaseAuthenticationException;
import com.yoyodev.springblog.common.exception.JwtVerificationException;
import com.yoyodev.springblog.common.model.UserPrincipal;
import com.yoyodev.springblog.common.framework.security.GrantedPermission;
import com.yoyodev.springblog.common.framework.security.SimpleAuthenticationToken;
import com.yoyodev.springblog.common.framework.security.UserPrincipalProvider;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserPrincipalProvider userPrincipalProvider;
    private final RedisCacheService redisCacheService;
    private final MessageSource messageSource;

    protected String getMessage(String code) {
        return messageSource.getMessage(code, null, getCurrentLocale());
    }

    protected String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, getCurrentLocale());
    }

    protected Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("Processing request " + request.getRequestURI());
        String token = getJwtFromRequest(request);
        try {
            String key = RedisConstants.BLACKLIST_TOKEN_KEY_PREFIX + RedisConstants.REDIS_KEY_SEPARATOR + token;
            if (redisCacheService.existsByKey(key)) {
                throw new BaseAuthenticationException(ErrorCode.AUTH_TOKEN_BLACKLISTED, getMessage("auth.token.invalid"));
            }

            String username = jwtProvider.proceedToken(token);
            UserPrincipal userPrincipal = userPrincipalProvider.getUserPrincipalByUsername(username);

            Set<GrantedPermission> authorities = userPrincipal.permissions().stream().map(GrantedPermission::new).collect(Collectors.toSet());

            SimpleAuthenticationToken authentication = SimpleAuthenticationToken.authenticated(userPrincipal, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (JOSEException | BadJWTException | ParseException | JwtVerificationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(getMessage("auth.token.invalid"));
            response.getWriter().flush();
        } catch (BaseAuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(getMessage("error.forbidden"));
            response.getWriter().flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(getMessage("error.internal.server"));
            response.getWriter().flush();
            log.error(getMessage("error.internal.server"), e);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || bearerToken.isEmpty()) throw new JwtVerificationException(getMessage("auth.token.invalid"));
        if (bearerToken.startsWith("Bearer ")) return bearerToken.substring(7);
        return bearerToken;
    }
}
