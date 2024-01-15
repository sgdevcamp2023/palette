package org.pallete.easelgatewayservice.jwt.component;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtProperties {

    @Value("${jwt.secret-key}")
    private String key;

    protected Key getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(this.key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
