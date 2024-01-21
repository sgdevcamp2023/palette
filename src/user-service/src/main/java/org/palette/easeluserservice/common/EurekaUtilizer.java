package org.palette.easeluserservice.common;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class EurekaUtilizer {

    private final EurekaClient eurekaClient;

    public Pair<String, Integer> getInstanceInfo(String serviceName) {
        final Application application = eurekaClient.getApplication(serviceName);
        final Random random = new Random();
        final List<InstanceInfo> instances = application.getInstances();
        final InstanceInfo instanceInfo = instances.get(random.nextInt(instances.size()));

        return new Pair<>(instanceInfo.getIPAddr(), instanceInfo.getPort());
    }
}
