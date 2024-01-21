package org.palette.easeluserservice.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.palette.easeluserservice.persistence.embed.*;
import org.palette.easeluserservice.persistence.enums.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Username username;

    @Embedded
    private Password password;

    @Embedded
    private Profile profile;

    @Embedded
    private PaintPin paintPin;

    @Embedded
    private DmPin dmPin;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "authed")
    private Boolean authed = true;

    @Column(name = "accessed_at")
    private LocalDateTime accessedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedDate
    private LocalDateTime deletedAt;

    public Boolean isUserNotAuthed() {
        return !this.getAuthed();
    }

    public void stampAccessedAt() {
        this.accessedAt = LocalDateTime.now();
    }

    public static User preJoin(
            String email,
            String nickname,
            String defaultStringValue
    ) {

        return User.builder()
                .email(new Email(email))
                .username(new Username(defaultStringValue))
                .password(new Password(defaultStringValue))
                .profile(
                        new Profile(
                                new Nickname(nickname),
                                new Introduce(defaultStringValue),
                                new StaticContentPath(
                                        defaultStringValue,
                                        defaultStringValue,
                                        defaultStringValue
                                )
                        )
                )
                .paintPin(new PaintPin(defaultStringValue))
                .dmPin(new DmPin(defaultStringValue))
                .accessedAt(null)
                .authed(false)
                .build();
    }

    public void join(
            String password,
            String username,
            Profile profile
    ) {
        this.password = new Password(password);
        this.username = new Username(username);
        this.profile = profile;
        this.role = Role.NORMAL;
    }
}
