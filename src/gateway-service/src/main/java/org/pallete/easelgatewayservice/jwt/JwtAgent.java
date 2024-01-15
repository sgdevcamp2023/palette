package org.pallete.easelgatewayservice.jwt;


import org.pallete.easelgatewayservice.jwt.component.JwtPair;

public interface JwtAgent {

    JwtPair provide(Long userId);

    void verify(String jwt);

    Long parseUserId(String jwt);
}
