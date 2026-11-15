package com.yoyodev.springblog.engine.controllers;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.yoyodev.springblog.engine.framework.aop.HasPermission;
import com.yoyodev.springblog.engine.framework.aop.PerformanceLog;
import com.yoyodev.springblog.engine.constants.EndpointConstants;
import com.yoyodev.springblog.common.enumerations.PerformanceLogType;
import com.yoyodev.springblog.common.enumerations.PermissionLevel;
import com.yoyodev.springblog.common.utils.AlgorithmHelper;
import com.yoyodev.springblog.core.entities.RefreshToken;
import com.yoyodev.springblog.engine.model.AuthModel;
import com.yoyodev.springblog.common.model.ResponseWrapper;
import com.yoyodev.springblog.engine.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(EndpointConstants.API_V1 + "/demo")
@RequiredArgsConstructor
public class DemoController extends BaseController {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/no-authentication")
    public ResponseEntity<ResponseWrapper<String>> demo() {
        return getSuccess();
    }

    @GetMapping("/with-authentication")
    @HasPermission(module = "00_GLOBAL", functionName = "MANAGE", level = PermissionLevel.Read_Write)
    public ResponseEntity<?> demoWithAuthentication() {
        return getSuccess();
    }

    @PostMapping("/get-error-jwt")
    @HasPermission(module = "00_GLOBAL", functionName = "MANAGE", level = PermissionLevel.Read_Write)
    @PerformanceLog(logType = {PerformanceLogType.TIME, PerformanceLogType.MEMORY})
    public ResponseEntity<?> getErrorJwt(@RequestBody Map<String, String> body) throws JOSEException {
        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder();
        body.forEach(
                (key, value) -> {
                    if (key.equals("iat") || key.equals("exp")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = null;
                        try {
                            date = sdf.parse(value);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        claimsBuilder.claim(key, date);
                    } else {
                        claimsBuilder.claim(key, value);
                    }
                }
        );

        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.RS256), claimsBuilder.build().toPayload());
        JWSSigner signer = new RSASSASigner(privateKey);
        jwsObject.sign(signer);
        String jwt = jwsObject.serialize();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(refreshTokenRepository.getMinUserId() - 1);
        String randomToken = UUID.randomUUID().toString();
        refreshToken.setToken(randomToken);
        refreshToken.setExpirationTime(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        refreshTokenRepository.save(refreshToken);

        return getSuccess(new AuthModel(jwt, AlgorithmHelper.generateMD5(refreshToken.getToken())));
    }
}
