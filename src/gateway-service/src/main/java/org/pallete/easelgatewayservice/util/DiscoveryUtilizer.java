package org.pallete.easelgatewayservice.util;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DiscoveryUtilizer {

    private static final String AUTH_SERVICE_ID = "AUTH-SERVICE";
    private static RoundRobinList<InstanceInfo> authInstancesRoundRobinList;

    private final EurekaClient discoveryClient;

    public InstanceInfo getLoadBalancedAuthServiceInstance() {
        return getRoundRobinInstance(authInstancesRoundRobinList);
    }

    private InstanceInfo getRoundRobinInstance(RoundRobinList<InstanceInfo> roundRobinList) {
        return roundRobinList.next();
    }

    @Scheduled(fixedDelay = 10000)
    public void renewAuthInstancesRoundRobinList() {
        final List<InstanceInfo> authServiceInstances = discoveryClient.getApplication(AUTH_SERVICE_ID).getInstances();
        if (authServiceInstances.isEmpty()) return;
        authInstancesRoundRobinList = new RoundRobinList<>(authServiceInstances);
    }
}
