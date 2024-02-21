package org.palette.easeluserservice.external;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.aop.InternalErrorLogging;
import org.palette.easeluserservice.persistence.User;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.palette.grpc.GAuthServiceGrpc;
import org.palette.grpc.GSendEmailAuthRequest;
import org.springframework.stereotype.Component;

@Component
public class GrpcAuthClient {

    @GrpcClient("auth-service")
    private GAuthServiceGrpc.GAuthServiceBlockingStub gAuthServiceBlockingStub;

    @InternalErrorLogging
    public void sendEmailAuth(User user) {
        try {
            gAuthServiceBlockingStub.sendEmailAuth(
                    GSendEmailAuthRequest.newBuilder()
                            .setEmail(user.getEmail())
                            .build()
            );
        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.USER_500_000001);
        }
    }
}
