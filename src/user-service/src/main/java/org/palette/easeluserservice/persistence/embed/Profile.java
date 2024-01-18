package org.palette.easeluserservice.persistence.embed;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Embedded
    private Nickname nickname;
    @Embedded
    private Introduce introduce;
    private String profileImagePath;
    private String backgroundImagePath;
    private String websitePath;

    public Profile(
            Nickname nickname,
            Introduce introduce,
            String profileImagePath,
            String backgroundImagePath,
            String websitePath
    ) {
        this.nickname = nickname;
        this.introduce = introduce;
        this.profileImagePath = profileImagePath;
        this.backgroundImagePath = backgroundImagePath;
        this.websitePath = websitePath;
    }
}
