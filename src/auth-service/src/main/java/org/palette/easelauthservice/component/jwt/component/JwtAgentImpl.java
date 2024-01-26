package org.palette.easelauthservice.component.jwt.component;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.component.jwt.JwtAgent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAgentImpl implements JwtAgent {

    private final JwtGenerator jwtGenerator;
    private final JwtVerifier jwtVerifier;
    private final JwtParser jwtParser;
    private static final int BEARER_PREFIX = 7;

    @Override
    public JwtPair provide(String email) {
        return jwtGenerator.execute(email);
    }

    @Override
    public void verify(String jwt) {
        jwtVerifier.execute(jwt);
    }

    @Override
    public Long parseUserId(String jwt) {
        jwt = jwt.substring(BEARER_PREFIX);
        jwtVerifier.execute(jwt);
        return jwtParser.getId(jwt);
    }
}
