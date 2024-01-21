package org.palette.easeluserservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.persistence.UserJpaRepository;
import org.palette.easeluserservice.persistence.embed.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private static final String DEFAULT_STRING_VALUE = "";

    private final UserJpaRepository userJpaRepository;

    @Transactional
    public User createTemporaryUser(
            String email,
            String nickname
    ) {
        User user = User.preJoin(email, nickname, DEFAULT_STRING_VALUE);

        userJpaRepository.save(user);

        return user;
    }

    @Transactional
    public User createCompletedUser(
            User user,
            String password,
            String username,
            Optional<String> introduce,
            Optional<String> profileImagePath,
            Optional<String> backgroundImagePath,
            Optional<String> websitePath
    ) {
        user.join(
                password,
                username,
                new Profile(
                        user.getProfile().nickname(),
                        new Introduce(introduce.orElse(DEFAULT_STRING_VALUE)),
                        new StaticContentPath(
                                profileImagePath.orElse(DEFAULT_STRING_VALUE),
                                backgroundImagePath.orElse(DEFAULT_STRING_VALUE),
                                websitePath.orElse(DEFAULT_STRING_VALUE)
                        )
                )
        );

        userJpaRepository.save(user);

        return user;
    }

    public Email isEmailAlreadyExists(String requestedEmail) {
        Email email = new Email(requestedEmail);
        if (userJpaRepository.existsByEmail(email)) throw new BaseException(ExceptionType.USER_000006);
        return email;
    }

    public Username isUsernameAlreadyExists(String requestedUsername) {
        Username username = new Username(requestedUsername);
        if (userJpaRepository.existsByUsername(username)) throw new BaseException(ExceptionType.USER_000006);
        return username;
    }

    public Optional<User> loadByEmail(Email email) {
        return userJpaRepository.findByEmail(email);
    }
}
