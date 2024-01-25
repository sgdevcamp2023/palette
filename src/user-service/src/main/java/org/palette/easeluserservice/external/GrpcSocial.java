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
            final GCreateUserResponse response = gSocialServiceBlockingStub.createUser(
                    GCreateUserRequest.newBuilder()
                            .setId(user.getId())
                            .setUsername(user.getUsername().value())
                            .setImagePath(user.getProfile().staticContentPath().profileImagePath())
                            .setNickname(user.getProfile().nickname().value())
                            .setIsActive(user.getAuthed())
                            .build());
            return response;

        } catch (final StatusRuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
