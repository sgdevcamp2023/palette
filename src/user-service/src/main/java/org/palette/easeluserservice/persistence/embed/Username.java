package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;

@Embeddable
public record Username(
        @Column(name = "username", nullable = false, unique = true, length = 50)
        String value
) {
    public Username {
        if (value.length() > 50) throw new BaseException(ExceptionType.USER_000001);
    }
}
