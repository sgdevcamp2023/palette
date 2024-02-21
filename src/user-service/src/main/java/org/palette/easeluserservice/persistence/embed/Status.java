package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
public record Status(
        @Column(name = "status", nullable = false)
        Boolean value
) {
}
