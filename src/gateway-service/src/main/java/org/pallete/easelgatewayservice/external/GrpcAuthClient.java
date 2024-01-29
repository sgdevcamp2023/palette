package org.pallete.easelgatewayservice.external;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.grpc.GAuthServiceGrpc;
import org.palette.grpc.GValidateJWTRequest;
import org.palette.grpc.GValidateJWTResponse;
import org.pallete.easelgatewayservice.exception.BaseException;
import org.pallete.easelgatewayservice.exception.ExceptionType;
import org.springframework.stereotype.Component;

@Component
public class GrpcAuthClient {

    @GrpcClient("auth-service")
    private GAuthServiceGrpc.GAuthServiceBlockingStub gAuthServiceBlockingStub;

    public GValidateJWTResponse validateJWT(String payload) {
        try {
            return gAuthServiceBlockingStub.validateJWT(
                    GValidateJWTRequest.newBuilder()
                            .setJwtPayload(payload)
                            .build()
            );

        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.GATEWAY_500_000001);
        }
    }
}
