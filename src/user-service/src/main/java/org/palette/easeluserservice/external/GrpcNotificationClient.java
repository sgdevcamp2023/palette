package org.palette.easeluserservice.external;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.aop.InternalErrorLogging;
import org.palette.easeluserservice.persistence.User;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.palette.grpc.GCreateNotificationUserRequest;
import org.palette.grpc.GNotificationServiceGrpc;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GrpcNotificationClient {

    @GrpcClient("auth-service")
    private GNotificationServiceGrpc.GNotificationServiceBlockingStub gNotificationServiceBlockingStub;

    @InternalErrorLogging
    @Transactional
    public void createNotificationUser(User user) {
        try {
            gNotificationServiceBlockingStub.createNotificationUser(
                    GCreateNotificationUserRequest.newBuilder()
                            .setNickname(user.getProfile().nickname())
                            .build()
            );
        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.USER_500_000001);
        }
    }
}
