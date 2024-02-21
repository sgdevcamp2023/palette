package org.palette.easeltimelineservice;

import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.LoggingAspect;
import org.palette.aop.PassportAspect;
import org.palette.config.CommonModuleConfig;
import org.palette.config.KafkaConsumerConfig;
import org.palette.config.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        CommonModuleConfig.class,
        EaselAuthenticationContext.class,
        PassportAspect.class,
        KafkaConsumerConfig.class,
        KafkaProducerConfig.class,
        LoggingAspect.class
})
public class EaselTimelineServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EaselTimelineServiceApplication.class, args);
    }

}
