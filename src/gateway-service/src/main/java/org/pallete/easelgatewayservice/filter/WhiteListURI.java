package org.pallete.easelgatewayservice.filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WhiteListURI {
    EMAIL_DUPLICATE_VERIFICATION("/api/users/verify-email"),
    USERNAME_DUPLICATE_VERIFICATION("/api/users/verify-username"),
    TEMPORARY_JOIN("/api/users/temporary-join"),
    RESEND_AUTH_EMAIL("/api/auth/resend"),
    EMAIL_AUTH("/api/auth"),
    JOIN("/api/users/join"),
    WEB_LOGIN("/api/auth/web"),
    MOBILE_LOGIN("/api/auth/mobile"),

    ;

    final String uri;
}
