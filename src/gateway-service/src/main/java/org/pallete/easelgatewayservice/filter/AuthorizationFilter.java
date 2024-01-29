package org.pallete.easelgatewayservice.filter;

import org.pallete.easelgatewayservice.external.GrpcAuthClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final GrpcAuthClient grpcAuthClient;

    public AuthorizationFilter(GrpcAuthClient grpcAuthClient) {
        this.grpcAuthClient = grpcAuthClient;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {
        if (isRequestContainsAuthorization(exchange)) {
            ServerHttpRequest request = exchange
                    .getRequest()
                    .mutate()
                    .header(
                            AUTHORIZATION_HEADER_NAME,
                            grpcAuthClient.validateJWT(extractJWT(exchange)).getPassportPayload())
                    .build();

            return chain.filter(
                    exchange.mutate()
                            .request(request)
                            .build()
            );
        }

        return null;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private String extractJWT(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER_NAME).get(0);
    }

    private boolean isRequestContainsAuthorization(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER_NAME).size() == 1;
    }
}
