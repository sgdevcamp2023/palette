package org.pallete.easelgatewayservice.jwt.component;

public record JwtPair(
        String accessToken,
        String refreshToken
) {
}
