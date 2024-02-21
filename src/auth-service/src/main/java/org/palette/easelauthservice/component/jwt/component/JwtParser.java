package org.palette.easelauthservice.component.jwt.component;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtParser {

    private static final String ID_FILED = "id";

    private final JwtProperties jwtProperties;
    private final JwtVerifier jwtVerifier;

    public Long getId(String token) {
        jwtVerifier.execute(token);

        Object id = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody().get(ID_FILED);

        return Long.valueOf(String.valueOf(id));
    }
}
