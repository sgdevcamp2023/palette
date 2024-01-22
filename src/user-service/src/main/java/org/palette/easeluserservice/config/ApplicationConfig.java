package org.palette.easeluserservice.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDiscoveryClient
@EnableJpaAuditing
@EnableAsync
public class ApplicationConfig {
}
