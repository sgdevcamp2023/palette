package org.palette.easelauthservice.component.jwt.component;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtParser {

    private static final String EMAIL_FILED = "email";

    private final JwtProperties jwtProperties;
    private final JwtVerifier jwtVerifier;

    public Long getEmail(String token) {
        jwtVerifier.execute(token);
        Object id = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody().get(EMAIL_FILED);
        return Long.valueOf(String.valueOf(id));
    }
}
