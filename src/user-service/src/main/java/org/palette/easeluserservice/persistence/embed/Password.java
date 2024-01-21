package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.palette.easeluserservice.common.BcryptPasswordEncoder;
import org.palette.easeluserservice.common.PasswordEncoder;

@Embeddable
public record Password(
        @Column(name = "password", nullable = false, length = 100)
        String value
) {
    public Password(String value) {
        this.value = passwordEncoder.encode(value);
    }

    private static final PasswordEncoder passwordEncoder = new BcryptPasswordEncoder();
}
