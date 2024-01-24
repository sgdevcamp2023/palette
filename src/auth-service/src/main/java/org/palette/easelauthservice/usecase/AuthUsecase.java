package org.palette.easelauthservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.dto.request.EmailAuthRequest;
import org.palette.easelauthservice.external.GrpcUser;
import org.palette.easelauthservice.redis.EmailAuth;
import org.palette.easelauthservice.redis.RedisEmailAuthService;
import org.palette.grpc.GAuthServiceGrpc;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthUsecase extends GAuthServiceGrpc.GAuthServiceImplBase {

    private final RedisEmailAuthService redisEmailAuthService;
    private final GrpcUser grpcUser;

    public void verify(EmailAuthRequest emailAuthRequest) {
        String email = emailAuthRequest.email();
        EmailAuth emailAuth = redisEmailAuthService.loadByEmail(email);
        emailAuth.isAbusing();
        if (emailAuth.comparePayload(emailAuth.getAuthPayload())) {
            grpcUser.updateUserAuthStatus(email);
        }
        emailAuth.decreaseThreshold();
        redisEmailAuthService.update(emailAuth);
    }
}
