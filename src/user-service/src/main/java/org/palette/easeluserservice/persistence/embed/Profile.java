package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public record Profile(
        String nickname,
        String introduce,
        @Embedded
        StaticContentPath staticContentPath
) {
}
