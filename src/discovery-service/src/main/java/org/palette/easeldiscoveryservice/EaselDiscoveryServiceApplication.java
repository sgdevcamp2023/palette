package org.palette.easeldiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EaselDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaselDiscoveryServiceApplication.class, args);
	}

}
