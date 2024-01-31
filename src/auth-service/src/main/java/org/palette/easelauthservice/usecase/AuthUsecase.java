package org.palette.easelauthservice.usecase;

import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.component.jwt.JwtAgent;
import org.palette.easelauthservice.component.jwt.component.JwtPair;
import org.palette.easelauthservice.component.mailsender.EmailAuthMailSender;
import org.palette.easelauthservice.dto.request.AuthEmailResendRequest;
import org.palette.easelauthservice.dto.request.EmailAuthRequest;
import org.palette.easelauthservice.dto.request.LoginRequest;
import org.palette.easelauthservice.external.GrpcUserClient;
import org.palette.easelauthservice.redis.EmailAuth;
import org.palette.easelauthservice.redis.RedisEmailAuthService;
import org.palette.grpc.GAuthServiceGrpc;
import org.palette.grpc.GLoadUserInfoFromIdResponse;
import org.palette.passport.PassportGenerator;
import org.palette.passport.component.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthUsecase extends GAuthServiceGrpc.GAuthServiceImplBase {

    private final RedisEmailAuthService redisEmailAuthService;
    private final EmailAuthMailSender emailAuthMailSender;
    private final GrpcUserClient grpcUserClient;
    private final JwtAgent jwtAgent;
    private final PassportGenerator passportGenerator;

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

    public JwtPair login(LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();

        return jwtAgent.provide(
                grpcUserClient.checkEmailWithPassword(email, password)
        );
    }

    public String validateJWT(String jwtPayload) {
        Long userId = jwtAgent.parseUserId(jwtPayload);
        GLoadUserInfoFromIdResponse gLoadUserInfoFromIdResponse = grpcUserClient.loadById(userId);

        return passportGenerator.generatePassport(
                new UserInfo(
                        userId,
                        gLoadUserInfoFromIdResponse.getEmail(),
                        gLoadUserInfoFromIdResponse.getNickname(),
                        gLoadUserInfoFromIdResponse.getUsername(),
                        gLoadUserInfoFromIdResponse.getRole(),
                        gLoadUserInfoFromIdResponse.getIsActivated(),
                        gLoadUserInfoFromIdResponse.getAccessedAt(),
                        gLoadUserInfoFromIdResponse.getCreatedAt(),
                        gLoadUserInfoFromIdResponse.getDeletedAt()
                )
        );
    }
}
