package org.palette.easelauthservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.palette.aop.InternalErrorLogging;
import org.palette.easelauthservice.component.cookie.AuthCookiePair;
import org.palette.easelauthservice.component.cookie.CookieAgent;
import org.palette.easelauthservice.component.cookie.LogoutCookiePair;
import org.palette.easelauthservice.component.jwt.component.JwtPair;
import org.palette.easelauthservice.dto.request.ResendEmailAuthRequest;
import org.palette.easelauthservice.dto.request.SendEmailAuthRequest;
import org.palette.easelauthservice.dto.request.LoginRequest;
import org.palette.easelauthservice.usecase.AuthUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthUsecase authUsecase;
    private final CookieAgent cookieAgent;

    @InternalErrorLogging
    @PostMapping
    public ResponseEntity<Void> auth(
            @RequestBody SendEmailAuthRequest sendEmailAuthRequest
    ) {
        authUsecase.verifyEmail(sendEmailAuthRequest);
        return ResponseEntity.ok().build();
    }

    @InternalErrorLogging
    @PostMapping("/resend")
    public ResponseEntity<Void> resend(
            @RequestBody ResendEmailAuthRequest resendEmailAuthRequest
    ) {
        authUsecase.resend(resendEmailAuthRequest);
        return ResponseEntity.ok().build();
    }

    @InternalErrorLogging
    @PostMapping("/web")
    public ResponseEntity<Void> webLogin(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse httpServletResponse
    ) {
        JwtPair jwtPair = authUsecase.login(loginRequest);
        AuthCookiePair authCookie = cookieAgent.createAuthCookie(jwtPair.accessToken(), jwtPair.refreshToken());
        httpServletResponse.addCookie(authCookie.accessTokenCookie());
        httpServletResponse.addCookie(authCookie.refreshTokenCookie());

        return ResponseEntity.ok().build();
    }

    @InternalErrorLogging
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletResponse httpServletResponse
    ) {
        LogoutCookiePair logoutCookie = cookieAgent.createLogoutCookie();
        httpServletResponse.addCookie(logoutCookie.accessTokenCookie());
        httpServletResponse.addCookie(logoutCookie.refreshTokenCookie());

        return ResponseEntity.ok().build();
    }

    @InternalErrorLogging
    @PostMapping("/mobile")
    public ResponseEntity<JwtPair> mobileLogin(
            @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity
                .ok()
                .body(authUsecase.login(loginRequest));
    }

    @InternalErrorLogging
    @PostMapping("/passport")
    public ResponseEntity<String> generatePassport(
            @RequestHeader("Authorization") String jwtPayload
    ) {
        return ResponseEntity
                .ok()
                .body(authUsecase.validateJWT(jwtPayload));
    }
}
