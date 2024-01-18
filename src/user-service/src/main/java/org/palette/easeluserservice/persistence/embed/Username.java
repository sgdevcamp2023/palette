package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Username {
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String value;

    public Username(String value) {
        if (value.length() > 50) throw new BaseException(ExceptionType.USER_000001);
        this.value = value;
    }
}
