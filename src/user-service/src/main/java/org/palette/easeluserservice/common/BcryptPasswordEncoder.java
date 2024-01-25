package org.palette.easeluserservice.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BcryptPasswordEncoder implements PasswordEncoder {


    private static final BCryptPasswordEncoder passwordEncoder = bCryptPasswordEncoder();

    @Bean
    protected static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Boolean match(String inputPassword, String persistencePassword) {
        return passwordEncoder.matches(inputPassword, persistencePassword);
    }
}
