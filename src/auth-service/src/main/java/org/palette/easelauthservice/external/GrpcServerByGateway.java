package org.palette.easelauthservice.external;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.palette.easelauthservice.component.jwt.JwtAgent;
import org.palette.grpc.GAuthServiceGrpc;
import org.palette.grpc.GLoadUserInfoFromIdResponse;
import org.palette.grpc.GValidateJWTRequest;
import org.palette.grpc.GValidateJWTResponse;
import org.palette.passport.PassportGenerator;
import org.palette.passport.component.UserInfo;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerByGateway extends GAuthServiceGrpc.GAuthServiceImplBase {

    private final JwtAgent jwtAgent;
    private final PassportGenerator passportGenerator;
    private final GrpcUserClient grpcUserClient;

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
