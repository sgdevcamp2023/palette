package org.palette.easelsocialservice.service;


import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelsocialservice.dto.request.PaintCreateRequest;
import org.palette.easelsocialservice.persistence.PaintRepository;
import org.palette.easelsocialservice.persistence.UserRepository;
import org.palette.easelsocialservice.persistence.domain.Paint;
import org.palette.easelsocialservice.persistence.domain.User;
import org.palette.grpc.GCreateUserRequest;
import org.palette.grpc.GCreateUserResponse;
import org.palette.grpc.GSocialServiceGrpc;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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

    public void checkUserExists(Long userId) {
    }

    public void checkUserExists(List<Long> mentions) {
    }
}