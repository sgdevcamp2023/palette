package org.pallete.easelgatewayservice.jwt.component;

import lombok.RequiredArgsConstructor;
import org.pallete.easelgatewayservice.jwt.JwtAgent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAgentImpl implements JwtAgent {

    private final JwtGenerator jwtGenerator;
    private final JwtVerifier jwtVerifier;
    private final JwtParser jwtParser;

    @Override
    public JwtPair provide(Long userId) {
        return jwtGenerator.execute(userId);
    }

    @Override
    public void verify(String jwt) {
        jwtVerifier.execute(jwt);
    }

    @Override
    public Long parseUserId(String jwt) {
        jwt = jwt.substring(JwtConst.BEARER_PREFIX_LENGTH);
        jwtVerifier.execute(jwt);
        return jwtParser.getId(jwt);
    }
}
