package org.palette.passport.component;

public record Passport(
        UserInfo userInfo,
        String integrityKey
) {
}
