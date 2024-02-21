package org.palette.easelauthservice.config;

import jakarta.annotation.PostConstruct;
import net.devh.boot.grpc.common.util.GrpcUtils;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
public class GrpcConfig {
    private final EurekaRegistration eurekaRegistration;
    private final GrpcServerProperties grpcProperties;

    public GrpcConfig(
            @Qualifier("eurekaRegistration") EurekaRegistration eurekaRegistration,
            GrpcServerProperties grpcProperties
    ) {
        this.eurekaRegistration = eurekaRegistration;
        this.grpcProperties = grpcProperties;
    }

    @PostConstruct
    public void init() {
        final int port = grpcProperties.getPort();
        eurekaRegistration.getInstanceConfig()
                .getMetadataMap()
                .put(GrpcUtils.CLOUD_DISCOVERY_METADATA_PORT, Integer.toString(port));
    }
}
