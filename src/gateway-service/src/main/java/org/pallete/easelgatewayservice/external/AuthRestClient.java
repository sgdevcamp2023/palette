package org.pallete.easelgatewayservice.external;

import lombok.RequiredArgsConstructor;
import org.pallete.easelgatewayservice.util.DiscoveryUtilizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Qualifier("AuthRestClient")
public class AuthRestClient implements AuthClient {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String GET_PASSPORT_URI = "passport";
    private final RestClient restClient = RestClient.create();
    private final DiscoveryUtilizer discoveryUtilizer;

    @Override
    public String validateAndProvidedPassport(final String jwtPayload) {
        return restClient
                .post()
                .uri(discoveryUtilizer.getLoadBalancedAuthServiceInstance().getHomePageUrl() + GET_PASSPORT_URI)
                .header(AUTHORIZATION_HEADER_NAME, jwtPayload)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }
}
