package org.palette.easelauthservice.component.cookie;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieAgent {

    private static final String ACCESS_TOKEN_COOKIE_KEY = "accessToken";
    private static final String REFRESH_TOKEN_COOKIE_KEY = "refreshToken";

    private static final int COOKIE_EXPIRED_TIME = 60 * 60;

    public Cookie createAccessToken(String value) {
        return createCookie(ACCESS_TOKEN_COOKIE_KEY, value);
    }

    public Cookie createRefreshToken(String value) {
        return createCookie(REFRESH_TOKEN_COOKIE_KEY, value);
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(COOKIE_EXPIRED_TIME);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

}
