package org.palette.easeluserservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.service.UserService;
import org.palette.grpc.*;
import org.springframework.transaction.annotation.Transactional;

@GrpcService
@RequiredArgsConstructor
public class GrpcServer extends GUserServiceGrpc.GUserServiceImplBase {

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
                .setIsSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkEmailAndPassword(
            GCheckEmailAndPasswordRequest request,
            StreamObserver<GCheckEmailAndPasswordResponse> responseObserver
    ) {
        User user = userService.loadByEmail(request.getEmail());

        userService.checkEmailAndPasswordByUser(
                user,
                request.getEmail(),
                request.getPassword()
        );

        user.stampAccessedAt();

        GCheckEmailAndPasswordResponse response = GCheckEmailAndPasswordResponse.newBuilder()
                .setIsSuccess(true)
                .setUserId(user.getId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void loadUserInfoFromId(
            GLoadUserInfoFromIdRequest request,
            StreamObserver<GLoadUserInfoFromIdResponse> responseObserver
    ) {
        User user = userService.loadById(request.getId());

        if (user.isNotDeleted()) {
            responseObserver.onNext(GLoadUserInfoFromIdResponse.newBuilder()
                    .setEmail(user.getEmail())
                    .setNickname(user.getProfile().nickname())
                    .setUsername(user.getUsername())
                    .setRole(user.getRole().name())
                    .setIsActivated(user.getIsActivated())
                    .setAccessedAt(user.getAccessedAt().toString())
                    .setCreatedAt(user.getCreatedAt().toString())
                    .setDeletedAt("")
                    .build()
            );
            responseObserver.onCompleted();
            return;
        }

        responseObserver.onNext(GLoadUserInfoFromIdResponse.newBuilder()
                .setEmail(user.getEmail())
                .setNickname(user.getProfile().nickname())
                .setUsername(user.getUsername())
                .setRole(user.getRole().name())
                .setIsActivated(user.getIsActivated())
                .setAccessedAt(user.getAccessedAt().toString())
                .setCreatedAt(user.getCreatedAt().toString())
                .setDeletedAt(user.getStringDeletedAt().toString())
                .build()
        );
        responseObserver.onCompleted();
    }
}
