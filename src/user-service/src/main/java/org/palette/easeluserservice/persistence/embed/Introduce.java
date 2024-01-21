package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;

@Embeddable
public record Introduce(
        @Column(name = "introduce", nullable = false, length = 160)
        String value
) {

    public Introduce {
        if (value.length() > 160) throw new BaseException(ExceptionType.USER_000001);
    }
}
