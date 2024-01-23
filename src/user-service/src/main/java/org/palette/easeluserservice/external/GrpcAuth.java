package org.palette.easeluserservice.external;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.easeluserservice.persistence.User;
import org.palette.grpc.GAuthServiceGrpc;
import org.palette.grpc.GSendEmailAuthRequest;
import org.palette.grpc.GSendEmailAuthResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcAuth {

    @GrpcClient("auth-service")
    private GAuthServiceGrpc.GAuthServiceBlockingStub gAuthServiceBlockingStub;

    public GSendEmailAuthResponse sendEmailAuth(User user) {
        // TODO: 매개변수 및 반환값 변경, 예외처리
        try {
            return gAuthServiceBlockingStub.sendEmailAuth(
                    GSendEmailAuthRequest.newBuilder()
                            .setId(user.getId())
                            .setEmail(user.getEmail())
                            .setNickname(user.getProfile().nickname())
                            .build()
            );
        } catch (final StatusRuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
