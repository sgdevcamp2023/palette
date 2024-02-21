package org.palette.easelauthservice.component.jwt.component;

public record JwtPair(
        String accessToken,
        String refreshToken
) {
}
