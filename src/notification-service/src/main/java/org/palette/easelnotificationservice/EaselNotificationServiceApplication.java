package org.palette.easelnotificationservice;

import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.PassportAspect;
import org.palette.config.CommonModuleConfig;
import org.palette.config.KafkaConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        CommonModuleConfig.class,
        EaselAuthenticationContext.class,
        PassportAspect.class,
        KafkaConsumerConfig.class
})
@SpringBootApplication
public class EaselNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EaselNotificationServiceApplication.class, args);
    }
}
