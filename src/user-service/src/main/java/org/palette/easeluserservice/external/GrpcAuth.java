package org.palette.easeluserservice.external;

import com.netflix.discovery.shared.Pair;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.common.EurekaUtilizer;
import org.palette.grpc.GAuthServiceGrpc;
import org.palette.grpc.GSendEmailAuthRequest;
import org.palette.grpc.GSendEmailAuthResponse;
import org.springframework.stereotype.Component;

import static org.palette.easeluserservice.common.EurekaServiceName.AUTH_SERVICE_NAME;

@Component
@RequiredArgsConstructor
public class GrpcAuth {

    private final EurekaUtilizer eurekaUtilizer;

    public GSendEmailAuthResponse sendEmailAuth(GSendEmailAuthRequest request) {
        final Pair<String, Integer> instanceInfo = eurekaUtilizer.getInstanceInfo(
                AUTH_SERVICE_NAME.getValue()
        );
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(
                        instanceInfo.first(),
                        instanceInfo.second()
                )
                .usePlaintext()
                .build();

        final GSendEmailAuthResponse response = GAuthServiceGrpc
                .newBlockingStub(channel)
                .sendEmailAuth(request);
        channel.shutdown();

        return response;
    }
}
