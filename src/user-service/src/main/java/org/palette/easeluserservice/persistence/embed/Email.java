package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;

@Embeddable
public class Email {
    @Column(name = "email", nullable = false, unique = true, length = 70)
    private String value;

    public Email(String value) {
        if (value.length() > 70) throw new BaseException(ExceptionType.USER_000001);
        this.value = value;
    }
}
