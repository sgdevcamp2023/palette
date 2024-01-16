package org.palette.easeluserservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.persistence.UserJpaRepository;
import org.palette.easeluserservice.persistence.embed.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUser(
            String email,
            String password,
            String username,
            String nickname,
            Optional<String> introduce,
            Optional<String> profileImagePath,
            Optional<String> backgroundImagePath,
            Optional<String> websitePath
    ) {
        User user = User.builder()
                .email(isEmailAlreadyExists(email))
                .username(isUsernameAlreadyExists(username))
                .password(new Password(password, bCryptPasswordEncoder))
                .profile(
                        new Profile(
                                new Nickname(nickname),
                                new Introduce(introduce.orElse("")),
                                profileImagePath.orElse(""),
                                backgroundImagePath.orElse(""),
                                websitePath.orElse("")
                        ))
                .paintPin(new PaintPin())
                .dmPin(new DmPin())
                .accessedAt(LocalDateTime.now())
                .build();
        userJpaRepository.save(user);
        return user;
    }

    public Email isEmailAlreadyExists(String requestedEmail) {
        Email email = new Email(requestedEmail);
        if (userJpaRepository.existsByEmail(email)) throw new BaseException(ExceptionType.USER_000006);
        return email;
    }

    private Username isUsernameAlreadyExists(String requestedUsername) {
        Username username = new Username(requestedUsername);
        if (userJpaRepository.existsByUsername(username)) throw new BaseException(ExceptionType.USER_000006);
        return username;
    }
}
