package org.pallete.easelgatewayservice.jwt.component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.pallete.easelgatewayservice.exception.BaseException;
import org.pallete.easelgatewayservice.exception.ExceptionType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtVerifier {

    private final JwtProperties jwtProperties;

    protected void execute(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSigningKey()).build()
                    .parseClaimsJws(token);
        } catch (MalformedJwtException | IllegalArgumentException ex) {
            throw new BaseException(ExceptionType.GATEWAY_000007);
        } catch (ExpiredJwtException exception) {
            throw new BaseException(ExceptionType.GATEWAY_000008);
        }
    }
}
