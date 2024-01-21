package org.palette.easeluserservice.external;

import com.netflix.discovery.shared.Pair;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.common.EurekaUtilizer;
import org.palette.grpc.GCreateUserRequest;
import org.palette.grpc.GCreateUserResponse;
import org.palette.grpc.GSocialServiceGrpc;
import org.springframework.stereotype.Component;

import static org.palette.easeluserservice.common.EurekaServiceName.SOCIAL_SERVICE_NAME;

@Component
@RequiredArgsConstructor
public class gRPCSocial {

    private final EurekaUtilizer eurekaUtilizer;

    public GCreateUserResponse createSocialUser(GCreateUserRequest request) {
        final Pair<String, Integer> instanceInfo = eurekaUtilizer.getInstanceInfo(
                SOCIAL_SERVICE_NAME.name()
        );
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(
                        instanceInfo.first(),
                        instanceInfo.second()
                )
                .usePlaintext()
                .build();
        final GCreateUserResponse response = GSocialServiceGrpc
                .newBlockingStub(channel)
                .createSocialUser(request);
        channel.shutdown();

        return response;
    }
}
