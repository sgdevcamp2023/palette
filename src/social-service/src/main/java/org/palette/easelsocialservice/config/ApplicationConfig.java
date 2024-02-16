package org.palette.easelsocialservice.config;

import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.PassportAspect;
import org.palette.config.CommonModuleConfig;
import org.palette.config.KafkaProducerConfig;
import org.palette.easelsocialservice.common.Base62PathEncoder;
import org.palette.easelsocialservice.common.PathEncoder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        CommonModuleConfig.class,
        EaselAuthenticationContext.class,
        PassportAspect.class,
        KafkaProducerConfig.class
})
@EnableDiscoveryClient
@Configuration
public class ApplicationConfig {

    @Bean
    public PathEncoder pathEncoder() {
        return new Base62PathEncoder();
    }
}
