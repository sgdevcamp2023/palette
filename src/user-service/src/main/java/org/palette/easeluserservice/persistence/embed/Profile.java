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

    public Profile updateNickname(String nickname) {
        return new Profile(
                nickname,
                this.introduce,
                this.staticContentPath
        );
    }

    public Profile updateIntroduce(String introduce) {
        return new Profile(
                this.nickname,
                introduce,
                this.staticContentPath
        );
    }

    public Profile updateStaticContentPath(StaticContentPath staticContentPath) {
        return new Profile(
                this.nickname,
                this.introduce,
                staticContentPath
        );
    }

}
