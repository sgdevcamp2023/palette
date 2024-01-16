package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Username {
    @Column(name = "username", nullable = false, unique = true, length = 50)
    @Size(max = 70, message = "email must be less than 70 characters.")
    private String value;
}
