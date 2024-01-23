package org.palette.easelauthservice.external;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.grpc.GUpdateUserAuthStatusRequest;
import org.palette.grpc.GUpdateUserAuthStatusResponse;
import org.palette.grpc.GUserServiceGrpc;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcUser {

    @GrpcClient("user-service")
    private GUserServiceGrpc.GUserServiceBlockingStub gUserServiceBlockingStub;

    public GUpdateUserAuthStatusResponse updateUserAuthStatus(Long userId) {
        // TODO: 매개변수 및 반환값 변경, 예외처리
        try {
            return gUserServiceBlockingStub.updateUserAuthStatus(
                    GUpdateUserAuthStatusRequest.newBuilder()
                            .setId(userId)
                            .build()
            );
        } catch (final StatusRuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
