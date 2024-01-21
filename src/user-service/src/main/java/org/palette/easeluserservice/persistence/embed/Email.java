package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;

@Embeddable
public record Email(
        @Column(name = "email", nullable = false, unique = true, length = 70)
        String value
) {
    public Email {
        if (value.length() > 70) throw new BaseException(ExceptionType.USER_000001);
    }
}
