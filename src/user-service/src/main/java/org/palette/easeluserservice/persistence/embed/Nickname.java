package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    @Column(name = "nickname", nullable = false, unique = true, length = 70)
    private String value;

    public Nickname(
            String nickname
    ) {
        this.value = nickname;
    }
}
