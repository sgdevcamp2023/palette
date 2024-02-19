package org.pallete.easelgatewayservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AUTH-SERVICE")
@Deprecated
public interface AuthFeignClient extends AuthClient {

    @Override
    @PostMapping("/passport")
    String validateAndProvidedPassport(
            @RequestHeader("Authorization") String jwtPayload
    );

}
