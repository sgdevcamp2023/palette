package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record DmPin(

        @Column(name = "dm_pin", nullable = false, length = Integer.MAX_VALUE)
        String value
) {
}
