package org.palette.easeluserservice.service;

import lombok.RequiredArgsConstructor;
import org.palette.easeluserservice.exception.BaseException;
import org.palette.easeluserservice.exception.ExceptionType;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.persistence.UserJpaRepository;
import org.palette.easeluserservice.persistence.embed.Profile;
import org.palette.easeluserservice.persistence.embed.StaticContentPath;
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
        User user = User.preJoin(
                email,
                nickname,
                DEFAULT_STRING_VALUE
        );

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
                        introduce.orElse(DEFAULT_STRING_VALUE),
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

    public void isEmailAlreadyExists(String requestedEmail) {
        if (userJpaRepository.existsByEmail(requestedEmail))
            throw new BaseException(ExceptionType.USER_409_000001);
    }

    public void isUsernameAlreadyExists(String requestedUsername) {
        if (userJpaRepository.existsByUsername(requestedUsername))
            throw new BaseException(ExceptionType.USER_409_000001);
    }

    public Optional<User> loadByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}
