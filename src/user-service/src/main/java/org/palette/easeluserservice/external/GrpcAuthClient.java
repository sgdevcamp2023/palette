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

@Component
public class GrpcAuthClient {

    @GrpcClient("notification-service")
    private GNotificationServiceGrpc.GNotificationServiceBlockingStub gNotificationServiceBlockingStub;

    @InternalErrorLogging
    public void sendEmailAuth(User user) {
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
