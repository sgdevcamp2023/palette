package org.palette.easelsocialservice;

import org.palette.config.CommonModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@Import({CommonModuleConfig.class})
@EnableDiscoveryClient
@SpringBootApplication
public class EaselSocialServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaselSocialServiceApplication.class, args);
	}

}
