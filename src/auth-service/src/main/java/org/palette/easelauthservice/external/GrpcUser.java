package org.palette.easelauthservice.external;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.easelauthservice.exception.BaseException;
import org.palette.easelauthservice.exception.ExceptionType;
import org.palette.grpc.GUpdateUserAuthStatusRequest;
import org.palette.grpc.GUpdateUserAuthStatusResponse;
import org.palette.grpc.GUserServiceGrpc;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcUser {

    @GrpcClient("user-service")
    private GUserServiceGrpc.GUserServiceBlockingStub gUserServiceBlockingStub;

    public GUpdateUserAuthStatusResponse updateUserAuthStatus(String email) {
        try {
            return gUserServiceBlockingStub.updateUserAuthStatus(
                    GUpdateUserAuthStatusRequest.newBuilder()
                            .setEmail(email)
                            .build()
            );
        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.AUTH_500_000001);
        }
    }
}
