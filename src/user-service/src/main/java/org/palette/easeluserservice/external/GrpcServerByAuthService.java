package org.palette.easeluserservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.service.UserService;
import org.palette.grpc.GUpdateUserAuthStatusRequest;
import org.palette.grpc.GUpdateUserAuthStatusResponse;
import org.palette.grpc.GUserServiceGrpc;
import org.springframework.transaction.annotation.Transactional;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerByAuthService extends GUserServiceGrpc.GUserServiceImplBase {

    private final UserService userService;

    @Transactional
    @Override
    public void updateUserAuthStatus(
            GUpdateUserAuthStatusRequest request,
            StreamObserver<GUpdateUserAuthStatusResponse> responseObserver
    ) {
        User user = userService.loadByEmail(request.getEmail());

        userService.updateUserAuthStatus(user);

        GUpdateUserAuthStatusResponse response = GUpdateUserAuthStatusResponse.newBuilder()
                .setMessage(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
