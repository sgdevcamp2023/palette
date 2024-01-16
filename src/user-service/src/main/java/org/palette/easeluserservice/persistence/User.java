package org.palette.easeluserservice.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.palette.easeluserservice.persistence.embed.*;
import org.palette.easeluserservice.persistence.enums.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
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

    @Column(name = "accessed_at")
    private LocalDateTime accessedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedDate
    private LocalDateTime deletedAt;

    public User stampAccessedAt() {
        this.accessedAt = LocalDateTime.now();
        return this;
    }
}
