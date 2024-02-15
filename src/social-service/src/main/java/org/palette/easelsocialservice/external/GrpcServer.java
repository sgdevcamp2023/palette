package org.palette.easelsocialservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelsocialservice.dto.response.PaintResponse;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.easelsocialservice.service.PaintService;
import org.palette.easelsocialservice.service.UserService;
import org.palette.grpc.*;

import java.util.ArrayList;
import java.util.List;


@GrpcService
@RequiredArgsConstructor
public class GrpcServer extends GSocialServiceGrpc.GSocialServiceImplBase {
    private final PaintService paintService;
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
    public void getFollowerIds(
            final GFollowerIdsRequest request,
            final StreamObserver<GFollowerIdsResponse> responseObserver
    ) {
        List<Long> followerIds = userService.getFollowerIds(request.getUserId());
        GFollowerIdsResponse response = GFollowerIdsResponse.newBuilder()
                .addAllFollowerIds(followerIds)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void loadUserFollowInformation(
            final GLoadUserFollowInformationRequest request,
            final StreamObserver<GLoadUserFollowInformationResponse> responseObserver
    ) {
        final long followingCount = userService.getFollowingCount(request.getPassport().getId());
        final long followerCount = userService.getFollowerCount(request.getPassport().getId());

        GLoadUserFollowInformationResponse response = GLoadUserFollowInformationResponse.newBuilder()
                .setFollowerCount(followingCount)
                .setFollowingCount(followerCount)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPaintsByIds(
            final GGetPaintsByIdsRequest request,
            final StreamObserver<GGetPaintsByIdsResponse> responseObserver
    ) {
        List<PaintResponse> paints = paintService.getAllPaintsByPid(request.getPaintIdsList());
        List<GPaintResponse> gpaints = convertToGPaintResponses(paints);

        GGetPaintsByIdsResponse response = GGetPaintsByIdsResponse.newBuilder()
                .addAllPaints(gpaints)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private List<GPaintResponse> convertToGPaintResponses(final List<PaintResponse> paints) {
        List<GPaintResponse> result = new ArrayList<>();
        return result;
    }

    private User convertToUser(final GCreateUserRequest request) {
        return new User(request.getId(), request.getUsername(), request.getNickname(), request.getImagePath(), request.getIsActive());
    }
}
