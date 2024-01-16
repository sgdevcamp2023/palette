package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
public class DmPin {

    @Column(name = "dm_pin", columnDefinition = "TEXT")
    private String value;
}
