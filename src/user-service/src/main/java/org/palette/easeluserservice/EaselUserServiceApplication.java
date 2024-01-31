package org.palette.easeluserservice;

import org.palette.config.CommonModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({CommonModuleConfig.class})
@SpringBootApplication
public class EaselUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaselUserServiceApplication.class, args);
	}

}
