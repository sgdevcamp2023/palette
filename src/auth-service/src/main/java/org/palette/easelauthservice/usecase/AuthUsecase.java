package org.palette.easelauthservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.component.mailsender.EmailAuthMailSender;
import org.palette.easelauthservice.dto.request.AuthEmailResendRequest;
import org.palette.easelauthservice.dto.request.EmailAuthRequest;
import org.palette.easelauthservice.dto.request.LoginRequest;
import org.palette.easelauthservice.external.GrpcUserClient;
import org.palette.easelauthservice.redis.EmailAuth;
import org.palette.easelauthservice.redis.RedisEmailAuthService;
import org.palette.grpc.GAuthServiceGrpc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthUsecase extends GAuthServiceGrpc.GAuthServiceImplBase {

    private final RedisEmailAuthService redisEmailAuthService;
    private final EmailAuthMailSender emailAuthMailSender;
    private final GrpcUserClient grpcUserClient;

    public void verifyEmail(EmailAuthRequest emailAuthRequest) {
        String email = emailAuthRequest.email();
        EmailAuth emailAuth = redisEmailAuthService.loadByEmail(email);
        emailAuth.isAbusing();
        if (emailAuth.comparePayload(emailAuth.getAuthPayload())) {
            grpcUserClient.updateUserAuthStatus(email);
            redisEmailAuthService.delete(emailAuth);
            return;
        }
        emailAuth.decreaseThreshold();
        redisEmailAuthService.update(emailAuth);
    }

    public void resend(AuthEmailResendRequest authEmailResendRequest) {
        String email = authEmailResendRequest.email();
        EmailAuth emailAuth = redisEmailAuthService.loadByEmail(email);
        emailAuth.isAbusing();
        emailAuth.updateAuthPayload();
        emailAuth.decreaseThreshold();
        redisEmailAuthService.update(emailAuth);

        emailAuthMailSender.send(email, emailAuth.getAuthPayload());
    }

    public void webLogin(LoginRequest loginRequest) {
        String email = loginRequest.email();

    }
}
