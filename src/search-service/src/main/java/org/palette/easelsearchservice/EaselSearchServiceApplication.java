package org.palette.easelsearchservice;

import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.PassportAspect;
import org.palette.config.CommonModuleConfig;
import org.palette.config.KafkaConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@Import({
		CommonModuleConfig.class,
		EaselAuthenticationContext.class,
		PassportAspect.class,
		KafkaConsumerConfig.class
})
@EnableDiscoveryClient
@SpringBootApplication
public class EaselSearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaselSearchServiceApplication.class, args);
	}

}
