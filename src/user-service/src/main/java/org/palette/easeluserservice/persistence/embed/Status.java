package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Status {

    @Column(name = "status", nullable = false, length = 100)
    private Boolean value;
}
