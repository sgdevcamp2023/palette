package org.palette.easelauthservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class ApplicationConfig {

    @Bean
    public Random random() {
        return new Random();
    }
}
