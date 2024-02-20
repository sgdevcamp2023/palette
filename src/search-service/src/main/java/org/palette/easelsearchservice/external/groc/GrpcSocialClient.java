package org.palette.easelsearchservice.external.groc;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.palette.grpc.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrpcSocialClient {

    @GrpcClient("social-service")
    private GSocialServiceGrpc.GSocialServiceBlockingStub gSocialServiceBlockingStub;

    public GGetPaintsByIdsResponse getPaintsByIds(final List<Long> paintIds) {
        try {
            return gSocialServiceBlockingStub.getPaintsByIds(
                    GGetPaintsByIdsRequest.newBuilder()
                            .addAllPaintIds(paintIds)
                            .build());

        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.USER_500_000001);
        }

    }
}
