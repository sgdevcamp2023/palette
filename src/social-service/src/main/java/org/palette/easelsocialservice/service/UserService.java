package org.palette.easelsocialservice.service;


import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelsocialservice.exception.BaseException;
import org.palette.easelsocialservice.exception.ExceptionType;
import org.palette.easelsocialservice.persistence.UserRepository;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.grpc.GCreateUserRequest;
import org.palette.grpc.GCreateUserResponse;
import org.palette.grpc.GSocialServiceGrpc;

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

    public User getUser(final Long userId) {
        return userRepository.findByUid(userId)
                .orElseThrow(() -> new BaseException(ExceptionType.SOCIAL_400_000001));
    }

    public void checkUserExists(final List<Long> mentionIds) {
        if (!userRepository.existsByAllUidsIn(mentionIds)) {
            throw new BaseException(ExceptionType.SOCIAL_400_000001);
        }
    }
    public Map<Long, User> getUserMapByUids(final List<Long> uids) {
        List<User> users = userRepository.findAllByUids(uids);
        Map<Long, User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getUid(), user);
        }
        return userMap;
    }

    public List<User> getUsersByUids(final List<Long> uids) {
        return userRepository.findAllByUids(uids);
    }

    private User convertToUser(final GCreateUserRequest request) {
        return new User(request.getId(), request.getUsername(), request.getNickname(), request.getImagePath(), request.getIsActive());
    }

    public void likePaint(Long userId, Paint paint) {
        User user = getUser(userId);
        user.likePaint(paint);
        userRepository.save(user);
    }
}
