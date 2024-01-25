package org.palette.easeluserservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.service.UserService;
import org.palette.grpc.GUpdateUserAuthStatusRequest;
import org.palette.grpc.GUpdateUserAuthStatusResponse;
import org.palette.grpc.GUserServiceGrpc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<User> user = userService.loadByEmail(request.getEmail());
        if (user.isEmpty()) throw new BaseException(ExceptionType.USER_500_000001);
        userService.updateUserAuthStatus(user.get());

        GUpdateUserAuthStatusResponse response = GUpdateUserAuthStatusResponse.newBuilder()
                .setMessage(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
