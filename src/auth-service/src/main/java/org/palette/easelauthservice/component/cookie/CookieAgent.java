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

}
