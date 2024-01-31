package org.palette.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.palette.passport.HMACEncoder;
import org.palette.passport.PassportExtractor;
import org.palette.passport.PassportGenerator;
import org.palette.passport.PassportValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CommonModuleConfig {

    private final ObjectMapper objectMapper;

    @Value("${passport.algorithm}")
    String HMacAlgorithm;

    @Value("${passport.key}")
    String passportSecretKey;

    @Bean
    public HMACEncoder hmacEncoder() {
        return new HMACEncoder(HMacAlgorithm, passportSecretKey);
    }

    @Bean
    public PassportGenerator passportGenerator() {
        return new PassportGenerator(objectMapper, hmacEncoder());
    }

    @Bean
    public PassportValidator passportValidator() {
        return new PassportValidator(objectMapper, hmacEncoder());
    }

    @Bean
    public PassportExtractor passportExtractor() {
        return new PassportExtractor(objectMapper, passportValidator());
    }
}
