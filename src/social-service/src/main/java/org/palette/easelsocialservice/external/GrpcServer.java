package org.palette.easelsocialservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.easelsocialservice.service.UserService;
import org.palette.grpc.*;


@GrpcService
@RequiredArgsConstructor
public class GrpcServer extends GSocialServiceGrpc.GSocialServiceImplBase {
    private final UserService userService;

    @Override
    public void createUser(
            final GCreateUserRequest request,
            final StreamObserver<GCreateUserResponse> responseStreamObserver) {
        userService.createUser(convertToUser(request));
        GCreateUserResponse response = GCreateUserResponse.newBuilder().setIsSuccess(true).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void loadUserFollowInformation(
            final GLoadUserFollowInformationRequest request,
            final StreamObserver<GLoadUserFollowInformationResponse> responseObserver
    ) {
        final User user = userService.getUser(request.getPassport().getId());

        //TODO 유저로 팔로잉 관계 모두 조회
        //TODO 그 객체로 카운팅해서 넣기

        GLoadUserFollowInformationResponse response = GLoadUserFollowInformationResponse.newBuilder()
                .setFollowerCount(1L)
                .setFollowingCount(1L)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private User convertToUser(final GCreateUserRequest request) {
        return new User(request.getId(), request.getUsername(), request.getNickname(), request.getImagePath(), request.getIsActive());
    }
}
