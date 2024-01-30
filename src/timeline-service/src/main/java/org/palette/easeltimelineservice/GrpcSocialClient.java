package org.palette.easeltimelineservice;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.grpc.GFollowerIdsRequest;
import org.palette.grpc.GFollowerIdsResponse;
import org.palette.grpc.GSocialServiceGrpc;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcSocialClient {

    @GrpcClient("social-service")
    private GSocialServiceGrpc.GSocialServiceBlockingStub gSocialServiceBlockingStub;

    public GFollowerIdsResponse getFollowerIds(Long userId) {
        try {
            return gSocialServiceBlockingStub.getFollowerIds(GFollowerIdsRequest.newBuilder()
                    .setUserId(userId)
                    .build());
        } catch (final StatusRuntimeException e) {
            // TODO
            // 공통 모듈 Exception
            throw new RuntimeException(e);
        }

    }
}
