package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Nickname {

    @Column(name = "nickname", nullable = false, unique = true, length = 70)
    private String value;

    public Nickname(
            String nickname
    ) {
        this.value = nickname;
    }
}
