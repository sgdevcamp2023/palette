package org.pallete.easelgatewayservice.filter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WhiteListURI {
    EMAIL_DUPLICATE_VERIFICATION("/users/verify-email"),
    USERNAME_DUPLICATE_VERIFICATION("/users/verify-username"),
    TEMPORARY_JOIN("/users/temporary-join"),
    EMAIL_AUTH("/auth"),
    JOIN("/users/join"),
    WEB_LOGIN("/auth/web"),
    MOBILE_LOGIN("/mobile/web"),

    ;

    final String uri;
}
