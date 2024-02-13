package org.palette.easelauthservice.component.jwt.component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtVerifier {

    private final JwtProperties jwtProperties;

    public void execute(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSigningKey()).build()
                    .parseClaimsJws(token);
        } catch (MalformedJwtException | IllegalArgumentException ex) {
            throw new BaseException(ExceptionType.AUTH_400_000001);
        } catch (ExpiredJwtException exception) {
            throw new BaseException(ExceptionType.AUTH_403_000003);
        }
    }
}
