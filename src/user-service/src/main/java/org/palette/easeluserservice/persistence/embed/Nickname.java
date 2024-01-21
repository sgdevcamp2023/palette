package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record Nickname(

        @Column(name = "nickname", nullable = false, unique = true, length = 70)
        String value
) {
}
