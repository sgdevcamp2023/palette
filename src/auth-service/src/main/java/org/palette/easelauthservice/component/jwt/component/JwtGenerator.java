package org.palette.easelauthservice.component.jwt.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.palette.easelauthservice.component.jwt.component.JwtProperties.*;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    private final JwtProperties jwtProperties;

    public JwtPair execute(String email) {
        return new JwtPair(buildAccessToken(email), buildRefreshToken());
    }

    private String buildAccessToken(String email) {
        return Jwts.builder()
                .signWith(jwtProperties.getSigningKey())
                .setHeaderParam(JWT_HEADER_PARAM_KEY, JWT_HEADER_PARAM_VALUE)
                .setClaims(buildClaims(email))
                .setExpiration(buildExpiredAt(ACCESS_TOKEN_EXPIRE_TIME))
                .compact();
    }

    private String buildRefreshToken() {
        return Jwts.builder()
                .signWith(jwtProperties.getSigningKey())
                .setHeaderParam(JWT_HEADER_PARAM_KEY, JWT_HEADER_PARAM_VALUE)
                .setExpiration(buildExpiredAt(REFRESH_TOKEN_EXPIRE_TIME))
                .compact();
    }

    private Claims buildClaims(String email) {
        Claims claims = Jwts.claims();
        claims.put(JWT_CLAIMS_EMAIL_COMPONENT, email);
        return claims;
    }

    private Date buildExpiredAt(Long expiredTime) {
        return new Date(new Date().getTime() + expiredTime);
    }
}
