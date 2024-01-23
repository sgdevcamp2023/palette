package org.palette.easelsocialservice.config;

import org.palette.easelsocialservice.common.Base62PathEncoder;
import org.palette.easelsocialservice.common.PathEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public PathEncoder pathEncoder() {
        return new Base62PathEncoder();
    }
}
