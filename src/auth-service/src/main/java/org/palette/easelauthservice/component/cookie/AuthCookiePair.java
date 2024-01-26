package org.palette.easelauthservice.component.cookie;

import jakarta.servlet.http.Cookie;

public record AuthCookiePair(
        Cookie accessTokenCookie,
        Cookie refreshTokenCookie
) {
}
