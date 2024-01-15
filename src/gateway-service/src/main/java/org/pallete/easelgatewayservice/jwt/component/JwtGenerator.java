package org.pallete.easelgatewayservice.jwt.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.pallete.easelgatewayservice.jwt.component.JwtConst.*;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    private final JwtProperties jwtProperties;

    protected JwtPair execute(Long userId) {
        return new JwtPair(buildAccessToken(userId), buildRefreshToken());
    }

    private String buildAccessToken(Long userId) {
        return Jwts.builder()
                .signWith(jwtProperties.getSigningKey())
                .setHeaderParam(HEAD_PARAMETER_NAME, HEAD_PARAMETER_VALUE)
                .setClaims(buildClaims(userId))
                .setExpiration(buildExpiredAt(ACCESS_TOKEN_EXPIRE_TIME))
                .compact();
    }

    private String buildRefreshToken() {
        return Jwts.builder()
                .signWith(jwtProperties.getSigningKey())
                .setHeaderParam(HEAD_PARAMETER_NAME, HEAD_PARAMETER_VALUE)
                .setExpiration(buildExpiredAt(REFRESH_TOKEN_EXPIRE_TIME))
                .compact();
    }

    private Claims buildClaims(Long userId) {
        final Claims claims = Jwts.claims();
        claims.setSubject(CLAIMS_SUBJECT);
        claims.put(CLAIMS_FIRST_FILED, userId);
        return claims;
    }

    private Date buildExpiredAt(Long expiredType) {
        return new Date(new Date().getTime() + expiredType);
    }
}
