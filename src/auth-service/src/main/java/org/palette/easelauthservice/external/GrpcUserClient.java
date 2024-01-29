package org.palette.easelauthservice.external;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.easelauthservice.exception.BaseException;
import org.palette.easelauthservice.exception.ExceptionType;
import org.palette.grpc.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcUserClient {

    @GrpcClient("user-service")
    private GUserServiceGrpc.GUserServiceBlockingStub gUserServiceBlockingStub;

    public void updateUserAuthStatus(String email) {
        try {
            gUserServiceBlockingStub.updateUserAuthStatus(
                    GUpdateUserAuthStatusRequest.newBuilder()
                            .setEmail(email)
                            .build()
            );
        } catch (final Exception e) {
            throw new BaseException(ExceptionType.AUTH_500_000001);
        }
    }

    public void checkEmailWithPassword(String email, String password) {
        try {
            gUserServiceBlockingStub.checkEmailAndPassword(
                    GCheckEmailAndPasswordRequest.newBuilder()
                            .setEmail(email)
                            .setPassword(password)
                            .build()
            );
        } catch (final Exception e) {
            throw new BaseException(ExceptionType.AUTH_500_000001);
        }
    }

    public GLoadUserInfoFromIdResponse loadById(Long id) {
        try {
            return gUserServiceBlockingStub.loadUserInfoFromId(
                    GLoadUserInfoFromIdRequest.newBuilder()
                            .setId(id)
                            .build()
            );
        } catch (final Exception e) {
            throw new BaseException(ExceptionType.AUTH_500_000001);
        }
    }

}
