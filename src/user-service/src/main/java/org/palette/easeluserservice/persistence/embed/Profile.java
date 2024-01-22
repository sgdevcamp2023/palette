package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record Profile(
        @Embedded
        Nickname nickname,
        @Embedded
        Introduce introduce,
        @Embedded
        StaticContentPath staticContentPath
) {
}
