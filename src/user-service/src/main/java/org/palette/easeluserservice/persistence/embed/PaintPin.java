package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PaintPin extends Pin {

    @Column(name = "paint_pin", columnDefinition = "TEXT")
    private String value;
}
