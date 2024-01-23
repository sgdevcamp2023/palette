package org.palette.easelauthservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.dto.request.EmailAuthRequest;
import org.palette.easelauthservice.external.GrpcUser;
import org.palette.easelauthservice.redis.RedisService;
import org.palette.grpc.GAuthServiceGrpc;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUsecase extends GAuthServiceGrpc.GAuthServiceImplBase {

    private final RedisService redisService;
    private final GrpcUser grpcUser;

    public void verify(EmailAuthRequest emailAuthRequest) {
        Long userId = emailAuthRequest.userId();
        redisService.verifyByRequestUserAndPayload(
                String.valueOf(userId),
                emailAuthRequest.payload()
        );

        grpcUser.updateUserAuthStatus(userId);
    }
}

