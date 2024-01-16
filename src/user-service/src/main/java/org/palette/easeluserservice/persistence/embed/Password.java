package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Embeddable
public class Password {
    @Column(name = "password", nullable = false, length = 100)
    private String value;

    public Password(
            String password,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.value = bCryptPasswordEncoder.encode(password);
    }
}
