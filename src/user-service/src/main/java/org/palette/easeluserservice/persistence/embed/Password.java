package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import static org.palette.easeluserservice.config.ApplicationConfig.bCryptPasswordEncoder;

@Embeddable
public record Password(
        @Column(name = "password", nullable = false, length = 100)
        String value
) {
    public Password(String value) {
        this.value = bCryptPasswordEncoder().encode(value);
    }
}
