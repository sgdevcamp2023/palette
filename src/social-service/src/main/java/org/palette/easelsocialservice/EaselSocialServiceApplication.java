package org.palette.easelsocialservice;

import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.PassportAspect;
import org.palette.config.CommonModuleConfig;
import org.palette.config.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@Import({
        CommonModuleConfig.class,
        EaselAuthenticationContext.class,
        PassportAspect.class,
        KafkaProducerConfig.class
})
@EnableDiscoveryClient
@SpringBootApplication
public class EaselSocialServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EaselSocialServiceApplication.class, args);
    }

}
