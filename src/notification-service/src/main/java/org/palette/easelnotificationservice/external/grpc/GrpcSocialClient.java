package org.palette.easelnotificationservice.external.grpc;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.palette.aop.InternalErrorLogging;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.palette.grpc.GFollowerIdsRequest;
import org.palette.grpc.GFollowerIdsResponse;
import org.palette.grpc.GSocialServiceGrpc;
import org.springframework.stereotype.Component;

@Component
public class GrpcSocialClient {

    @GrpcClient("social-service")
    private GSocialServiceGrpc.GSocialServiceBlockingStub gSocialServiceBlockingStub;

    @InternalErrorLogging
    public GFollowerIdsResponse getPaintWriterFollowersIds(final Long writerId) {
        try {
            return gSocialServiceBlockingStub.getFollowerIds(
                    GFollowerIdsRequest.newBuilder()
                            .setUserId(writerId)
                            .build());
        } catch (final StatusRuntimeException e) {
            throw new BaseException(ExceptionType.NOTIFICATION_500_000001);
        }
    }
}
