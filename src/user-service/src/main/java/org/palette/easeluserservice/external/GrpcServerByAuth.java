package org.palette.easeluserservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.service.UserService;
import org.palette.grpc.GUpdateUserAuthStatusRequest;
import org.palette.grpc.GUpdateUserAuthStatusResponse;
import org.palette.grpc.GUserServiceGrpc;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GrpcServerByAuth extends GUserServiceGrpc.GUserServiceImplBase {

    private final UserService userService;

    @Override
    public void updateUserAuthStatus(
            GUpdateUserAuthStatusRequest request,
            StreamObserver<GUpdateUserAuthStatusResponse> responseObserver
    ) {
        Optional<User> user = userService.loadByEmail(request.getEmail());
        if (user.isEmpty()) throw new BaseException(ExceptionType.USER_500_000001);
        userService.updateUserAuthStatus(user.get());
    }
}
