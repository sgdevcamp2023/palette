package org.palette.easelsocialservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EaselSocialServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaselSocialServiceApplication.class, args);
	}

}
