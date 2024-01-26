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
        return new JwtPair(
                buildJwtToken(buildClaims(email), ACCESS_TOKEN_EXPIRE_TIME), 
                buildJwtToken(Jwts.claims(), REFRESH_TOKEN_EXPIRE_TIME)
        );
    }

    private String buildJwtToken(Claims claims, Long expiryTimeInMilliseconds) {
        return Jwts.builder()
                .signWith(jwtProperties.getSigningKey())
                .setHeaderParam(JWT_HEADER_PARAM_KEY, JWT_HEADER_PARAM_VALUE)
                .setClaims(claims)
                .setExpiration(getExpiryDate(expiryTimeInMilliseconds))
                .compact();
    }

    private Claims buildClaims(String email) {
        Claims claims = Jwts.claims();
        claims.put(JWT_CLAIMS_EMAIL_COMPONENT, email);
        return claims;
    }

    private Date getExpiryDate(Long expiryTimeInMilliseconds) {
        return new Date(new Date().getTime() + expiryTimeInMilliseconds);
    }
}
