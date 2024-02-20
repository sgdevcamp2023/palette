package org.palette.easeluserservice;

import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.PassportAspect;
import org.palette.config.CommonModuleConfig;
import org.palette.config.KafkaConsumerConfig;
import org.palette.config.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        CommonModuleConfig.class,
        EaselAuthenticationContext.class,
        PassportAspect.class,
        KafkaConsumerConfig.class,
        KafkaProducerConfig.class
})
@SpringBootApplication
public class EaselUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EaselUserServiceApplication.class, args);
    }

}
