package org.palette.easelsocialservice.service;


import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.palette.easelsocialservice.persistence.UserRepository;
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
    private final PaintRepository paintRepository;

    @Override
    public void createUser(GCreateUserRequest request, StreamObserver<GCreateUserResponse> responseStreamObserver) {
        // TODO: 중복 및 잘못된 값 예외처리
        userRepository.save(convertToUser(request));
        GCreateUserResponse response = GCreateUserResponse.newBuilder().setMessage(true).build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

    public User getUser(Long userId) {
        return userRepository.findByUid(userId).orElseThrow();
    }

    private User convertToUser(GCreateUserRequest request) {
        return new User(request.getId(), request.getUsername(), request.getNickname(), request.getImagePath(), request.getIsActive());
    }

    public void checkUserExists(List<Long> mentionIds) {
        if (!userRepository.existsByAllUidsIn(mentionIds)) {
            // TODO: 예외처리
            throw new RuntimeException("없는 사용자");
        }
    }

    public Map<Long, User> getUsersByUids(List<Long> uids) {
        List<User> users = userRepository.findAllByUids(uids);
        Map<Long, User> userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getUid(), user);
        }
        return userMap;
    }
}
