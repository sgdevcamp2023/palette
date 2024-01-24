package org.palette.easelauthservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.palette.easelauthservice.component.generator.AuthPayloadGenerator;
import org.palette.easelauthservice.component.mailsender.EmailAuthMailSender;
import org.palette.easelauthservice.redis.RedisEmailAuthService;
import org.palette.grpc.GAuthServiceGrpc;
import org.palette.grpc.GSendEmailAuthRequest;
import org.palette.grpc.GSendEmailAuthResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrpcServer extends GAuthServiceGrpc.GAuthServiceImplBase {

    private final RedisEmailAuthService redisEmailAuthService;
    private final EmailAuthMailSender emailAuthMailSender;
    private final AuthPayloadGenerator authPayloadGenerator;

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
                .setMessage(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
