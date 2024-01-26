package org.palette.easeluserservice.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.palette.easeluserservice.persistence.embed.Password;
import org.palette.easeluserservice.persistence.embed.Pin;
import org.palette.easeluserservice.persistence.embed.Profile;
import org.palette.easeluserservice.persistence.embed.StaticContentPath;
import org.palette.easeluserservice.persistence.enums.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
@DynamicUpdate
@SQLDelete(sql = "UPDATE user SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 70)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Embedded
    private Password password;

    @Embedded
    private Profile profile;

    @Embedded
    private Pin pin;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "authed")
    private Boolean authed = false;

    @Column(name = "is_activated")
    private Boolean isActivated = true;

    @Column(name = "accessed_at")
    private LocalDateTime accessedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt = null;

    public void updateToAuthed() {
        this.authed = true;
    }

    public Boolean isUserNotAuthed() {
        return !this.getAuthed();
    }

    public void stampAccessedAt() {
        this.accessedAt = LocalDateTime.now();
    }

    public static User preJoin(
            String email,
            String nickname,
            String defaultStringValue,
            PasswordEncoder passwordEncoder
    ) {
        return User.builder()
                .email(email)
                .username(nickname
                        + LocalDateTime.now().getYear()
                        + LocalDateTime.now().getMonth()
                        + LocalDateTime.now().getDayOfMonth()
                        + LocalDateTime.now().getHour()
                        + LocalDateTime.now().getMinute()
                        + LocalDateTime.now().getSecond()
                )
                .password(
                        new Password(defaultStringValue, passwordEncoder)
                )
                .profile(
                        new Profile(
                                nickname,
                                defaultStringValue,
                                new StaticContentPath(
                                        defaultStringValue,
                                        defaultStringValue,
                                        defaultStringValue
                                )
                        )
                )
                .role(Role.NORMAL)
                .pin(new Pin(defaultStringValue, defaultStringValue))
                .accessedAt(null)
                .authed(false)
                .isActivated(true)
                .build();
    }

    public void join(
            String password,
            PasswordEncoder passwordEncoder,
            String username,
            Profile profile
    ) {
        this.password = new Password(password, passwordEncoder);
        this.username = username;
        this.profile = profile;
        this.role = Role.NORMAL;
    }
}
