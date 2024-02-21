package org.palette.easelauthservice.component.jwt;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.component.jwt.component.JwtGenerator;
import org.palette.easelauthservice.component.jwt.component.JwtPair;
import org.palette.easelauthservice.component.jwt.component.JwtParser;
import org.palette.easelauthservice.component.jwt.component.JwtVerifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAgent {

    private final JwtGenerator jwtGenerator;
    private final JwtVerifier jwtVerifier;
    private final JwtParser jwtParser;
    private static final int BEARER_PREFIX = 7;

    public JwtPair provide(Long id) {
        return jwtGenerator.execute(id);
    }

    public void verify(String jwt) {
        jwtVerifier.execute(jwt);
    }

    public Long parseUserId(String jwt) {
        jwt = jwt.substring(BEARER_PREFIX);
        return jwtParser.getId(jwt);
    }
}
