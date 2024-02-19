package org.pallete.easelgatewayservice.external;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
public interface AuthClient {
    String validateAndProvidedPassport(@RequestHeader("Authorization") String jwtPayload);
}
