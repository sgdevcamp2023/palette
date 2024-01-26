package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.palette.easeluserservice.common.PasswordUtilizer;

@Embeddable
public record Password(
        @Column(name = "password", nullable = false, length = 100)
        String value
) {
    public Password(String value) {
        this.value = PasswordUtilizer.hashPassword(value);
    }
}
