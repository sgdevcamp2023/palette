package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Introduce {

    @Column(name = "introduce", length = 160)
    private String value;

    public Introduce(String value) {
        if (value.length() > 160) throw new BaseException(ExceptionType.USER_000001);
        this.value = value;
    }
}
