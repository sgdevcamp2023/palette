package org.palette.easelnotificationservice.external.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.aop.InternalErrorLogging;
import org.palette.easelnotificationservice.service.UserService;
import org.palette.grpc.GCreateNotificationUserRequest;
import org.palette.grpc.GCreateNotificationUserResponse;
import org.palette.grpc.GNotificationServiceGrpc;
import org.springframework.transaction.annotation.Transactional;

@GrpcService
@RequiredArgsConstructor
public class GrpcServer extends GNotificationServiceGrpc.GNotificationServiceImplBase {

    private final UserService userService;

    @InternalErrorLogging
    @Transactional
    @Override
    public void createNotificationUser(
            final GCreateNotificationUserRequest request,
            final StreamObserver<GCreateNotificationUserResponse> responseObserver
    ) {
        userService.create(request.getNickname());

        GCreateNotificationUserResponse response = GCreateNotificationUserResponse.newBuilder()
                .setIsSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
