package org.palette.easelauthservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.aop.InternalErrorLogging;
import org.palette.easelauthservice.component.generator.emailauth.AuthPayloadGenerator;
import org.palette.easelauthservice.component.mailsender.EmailAuthMailSender;
import org.palette.easelauthservice.redis.RedisEmailAuthService;
import org.palette.grpc.GAuthServiceGrpc;
import org.palette.grpc.GSendEmailAuthRequest;
import org.palette.grpc.GSendEmailAuthResponse;

@GrpcService
@RequiredArgsConstructor
public class GrpcServer extends GAuthServiceGrpc.GAuthServiceImplBase {

    private final RedisEmailAuthService redisEmailAuthService;
    private final EmailAuthMailSender emailAuthMailSender;

    @InternalErrorLogging
    @Override
    public void sendEmailAuth(
            GSendEmailAuthRequest request,
            StreamObserver<GSendEmailAuthResponse> responseObserver
    ) {

        String authPayload = AuthPayloadGenerator.execute();

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
}
