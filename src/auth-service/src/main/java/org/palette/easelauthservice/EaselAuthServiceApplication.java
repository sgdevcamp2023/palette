package org.palette.easelauthservice;

import org.palette.aop.EaselAuthenticationContext;
import org.palette.aop.LoggingAspect;
import org.palette.aop.PassportAspect;
import org.palette.config.CommonModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
		CommonModuleConfig.class,
		EaselAuthenticationContext.class,
		PassportAspect.class,
		LoggingAspect.class
})
@SpringBootApplication
public class EaselAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaselAuthServiceApplication.class, args);
	}

}
