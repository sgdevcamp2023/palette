package org.palette.easelauthservice.component.cookie;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieAgent {

    private static final String ACCESS_TOKEN_COOKIE_KEY = "accessToken";
    private static final String REFRESH_TOKEN_COOKIE_KEY = "refreshToken";
    private static final int COOKIE_EXPIRED_TIME = 60 * 60;

    public AuthCookiePair createAuthCookie(
            String accessToken,
            String refreshToken
    ) {
        return new AuthCookiePair(
                buildCookie(ACCESS_TOKEN_COOKIE_KEY, accessToken),
                buildCookie(REFRESH_TOKEN_COOKIE_KEY, refreshToken)
        );
    }

    public LogoutCookiePair createLogoutCookie() {
        return new LogoutCookiePair(
                buildLogoutCookie(ACCESS_TOKEN_COOKIE_KEY, ""),
                buildLogoutCookie(REFRESH_TOKEN_COOKIE_KEY, "")
        );
    }

    private Cookie buildCookie(
            String name,
            String value
    ) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(COOKIE_EXPIRED_TIME);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    private Cookie buildLogoutCookie(
            String name,
            String value
    ) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

}
