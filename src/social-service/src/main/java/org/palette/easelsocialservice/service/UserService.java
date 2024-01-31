package org.palette.easelsocialservice.service;


import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelsocialservice.exception.BaseException;
import org.palette.easelsocialservice.exception.ExceptionType;
import org.palette.easelsocialservice.persistence.UserRepository;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.grpc.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@GrpcService
@RequiredArgsConstructor
public class UserService extends GSocialServiceGrpc.GSocialServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void createUser(GCreateUserRequest request, StreamObserver<GCreateUserResponse> responseStreamObserver) {
        if (userRepository.existsByUid(request.getId())) {
            throw new BaseException(ExceptionType.SOCIAL_400_000003);
        }

        userRepository.save(convertToUser(request));
        GCreateUserResponse response = GCreateUserResponse.newBuilder().setIsSuccess(true).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    @Override
    public void loadUserFollowInformation(
            final GLoadUserFollowInformationRequest request,
            final StreamObserver<GLoadUserFollowInformationResponse> responseObserver
    ) {
        final User user = userRepository.findByUid(request.getPassport().getId()).orElseThrow(() -> {
            throw new BaseException(ExceptionType.SOCIAL_400_000001);
        });

        //TODO 유저로 팔로잉 관계 모두 조회
        //TODO 그 객체로 카운팅해서 넣기

        GLoadUserFollowInformationResponse response = GLoadUserFollowInformationResponse.newBuilder()
                .setFollowerCount(1L)
                .setFollowingCount(1L)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public User getUser(Long userId) {
        return userRepository.findByUid(userId)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000001));
    }

    public void checkUserExists(List<Long> mentionIds) {
        if (!userRepository.existsByAllUidsIn(mentionIds)) {
            throw new BaseException(ExceptionType.SOCIAL_400_000001);
        }
    }

    public Map<Long, User> getUserMapByUids(List<Long> uids) {
        List<User> users = userRepository.findAllByUids(uids);
        Map<Long, User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getUid(), user);
        }
        return userMap;
    }

    public List<User> getUsersByUids(List<Long> uids) {
        return userRepository.findAllByUids(uids);
    }

    private User convertToUser(GCreateUserRequest request) {
        return new User(request.getId(), request.getUsername(), request.getNickname(), request.getImagePath(), request.getIsActive());
    }
}
