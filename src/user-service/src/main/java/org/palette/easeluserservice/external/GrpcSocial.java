package org.palette.easeluserservice.external;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.easeluserservice.persistence.User;
import org.palette.grpc.GCreateUserRequest;
import org.palette.grpc.GCreateUserResponse;
import org.palette.grpc.GSocialServiceGrpc;
import org.springframework.stereotype.Component;

@Component
public class GrpcSocial {

    @GrpcClient("social-service")
    private GSocialServiceGrpc.GSocialServiceBlockingStub gSocialServiceBlockingStub;

    public GCreateUserResponse createSocialUser(User user) {
        // TODO: 매개변수 및 반환값 변경, 예외처리
        try {
            return gSocialServiceBlockingStub.createUser(
                    GCreateUserRequest.newBuilder()
                            .setId(user.getId())
                            .setUsername(user.getUsername())
                            .setImagePath(user.getProfile().staticContentPath().profileImagePath())
                            .setNickname(user.getProfile().nickname())
                            .setIsActive(user.getIsActivated())
                            .build());
        } catch (final StatusRuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
