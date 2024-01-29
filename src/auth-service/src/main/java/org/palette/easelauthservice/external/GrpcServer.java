package org.palette.easelauthservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelauthservice.component.generator.emailauth.AuthPayloadGenerator;
import org.palette.easelauthservice.component.jwt.JwtAgent;
import org.palette.easelauthservice.component.mailsender.EmailAuthMailSender;
import org.palette.easelauthservice.redis.RedisEmailAuthService;
import org.palette.grpc.*;
import org.palette.passport.PassportGenerator;
import org.palette.passport.component.UserInfo;

@GrpcService
@RequiredArgsConstructor
public class GrpcServer extends GAuthServiceGrpc.GAuthServiceImplBase {

    private final RedisEmailAuthService redisEmailAuthService;
    private final EmailAuthMailSender emailAuthMailSender;
    private final AuthPayloadGenerator authPayloadGenerator;
    private final JwtAgent jwtAgent;
    private final PassportGenerator passportGenerator;
    private final GrpcUserClient grpcUserClient;

    @Override
    public void sendEmailAuth(
            GSendEmailAuthRequest request,
            StreamObserver<GSendEmailAuthResponse> responseObserver
    ) {

        String authPayload = authPayloadGenerator.execute();

        emailAuthMailSender.send(
                request.getEmail(),
                authPayload
        );

        redisEmailAuthService.create(
                request.getEmail(),
                authPayload
        );

        GSendEmailAuthResponse response = GSendEmailAuthResponse.newBuilder()
                .setIsSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void validateJWT(
            GValidateJWTRequest request,
            StreamObserver<GValidateJWTResponse> responseObserver
    ) {
        Long userId = jwtAgent.parseUserId(request.getJwtPayload());

        GLoadUserInfoFromIdResponse gLoadUserInfoFromIdResponse = grpcUserClient.loadById(userId);

        String passportPayload = passportGenerator.generatePassport(
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

        GValidateJWTResponse response = GValidateJWTResponse.newBuilder()
                .setPassportPayload(passportPayload)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
