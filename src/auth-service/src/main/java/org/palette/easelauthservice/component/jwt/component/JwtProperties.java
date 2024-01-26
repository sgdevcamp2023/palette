package org.palette.easelauthservice.component.jwt.component;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtProperties {

    @Value("${jwt.secret-key}")
    private String key;

    protected static final String JWT_HEADER_PARAM_KEY = "type";
    protected static final String JWT_HEADER_PARAM_VALUE = "JWT";
    protected static final String JWT_CLAIMS_SUBJECT = "jwt";
    protected static final String JWT_CLAIMS_EMAIL_COMPONENT = "email";
    protected static final Long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;
    protected static final Long REFRESH_TOKEN_EXPIRE_TIME = 270 * 24 * 60 * 60 * 1000L;

    public Key getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(this.key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
