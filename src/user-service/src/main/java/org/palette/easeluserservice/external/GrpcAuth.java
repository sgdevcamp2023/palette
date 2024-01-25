package org.palette.easeluserservice.external;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;
import org.palette.easeluserservice.persistence.User;
import org.palette.grpc.GAuthServiceGrpc;
import org.palette.grpc.GSendEmailAuthRequest;
import org.palette.grpc.GSendEmailAuthResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcAuth {

    @GrpcClient("auth-service")
    private GAuthServiceGrpc.GAuthServiceBlockingStub gAuthServiceBlockingStub;

    public GSendEmailAuthResponse sendEmailAuth(User user) {
        try {
            return gAuthServiceBlockingStub.sendEmailAuth(
                    GSendEmailAuthRequest.newBuilder()
                            .setEmail(user.getEmail())
                            .build()
            );
        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.USER_500_000001);
        }
    }
}
