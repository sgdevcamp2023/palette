package org.pallete.easelgatewayservice.filter;

import org.pallete.easelgatewayservice.external.FeignAuthClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizationGatewayFilterFactory.Config> {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private final FeignAuthClient feignAuthClient;

    public AuthorizationGatewayFilterFactory(@Lazy FeignAuthClient feignAuthClient) {
        super(Config.class);
        this.feignAuthClient = feignAuthClient;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String uri = exchange.getRequest().getURI().getPath();

            if (isWhiteList(uri)) {
                return chain.filter(exchange);
            }

            if (isRequestContainsAuthorization(exchange)) {
                String passport = feignAuthClient.validateAndProvidedPassport(extractJWT(exchange));

                ServerHttpRequest request = exchange
                        .getRequest()
                        .mutate()
                        .header(
                                AUTHORIZATION_HEADER_NAME,
                                passport
                        )
                        .build();

                return chain.filter(exchange.mutate()
                        .request(request)
                        .build()
                );
            }
            return chain.filter(exchange);
        };
    }

    private String extractJWT(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER_NAME).get(0);
    }

    private boolean isRequestContainsAuthorization(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER_NAME).size() == 1;
    }

    private boolean isWhiteList(String target) {
        for (WhiteListURI whiteListURI : WhiteListURI.values()) {
            if (whiteListURI.uri.equals(target)) {
                return true;
            }
        }
        return false;
    }

}
