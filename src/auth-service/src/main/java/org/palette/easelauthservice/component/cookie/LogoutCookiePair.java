package org.palette.easelauthservice.component.cookie;

import jakarta.servlet.http.Cookie;

public record LogoutCookiePair(
        Cookie accessTokenCookie,
        Cookie refreshTokenCookie
) {
}
