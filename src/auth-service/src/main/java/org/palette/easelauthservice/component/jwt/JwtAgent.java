package org.palette.easelauthservice.component.jwt;

import org.palette.easelauthservice.component.jwt.component.JwtPair;

public interface JwtAgent {

    JwtPair provide(String email);

    void verify(String jwt);

    Long parseUserId(String jwt);
}
