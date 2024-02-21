package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.palette.exception.BaseException;
import org.palette.exception.ExceptionType;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    @Column(name = "password", nullable = false, length = 100)
    String value;

    public Password(String value, PasswordEncoder passwordEncoder) {
        this.value = passwordEncoder.encode(value);
    }

    public void match(String requestedPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(requestedPassword, this.value)) {
            throw new BaseException(ExceptionType.USER_400_000002);
        }
    }
}
