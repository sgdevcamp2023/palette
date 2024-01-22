package org.palette.easeluserservice.e2e.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easeluserservice.dto.request.JoinRequest;
import org.palette.easeluserservice.e2e.AcceptanceTestBase;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.persistence.UserJpaRepository;
import org.palette.easeluserservice.persistence.embed.*;
import org.palette.easeluserservice.persistence.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Join extends AcceptanceTestBase {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @BeforeEach
    public void insertTemporaryUser() throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {

        if (userJpaRepository.existsByEmail(new Email("diger@gmail.com"))) return;

        Class<User> userClass = User.class;
        Constructor<User> constructor = userClass.getDeclaredConstructor(
                Long.class,
                Email.class,
                Username.class,
                Password.class,
                Profile.class,
                PaintPin.class,
                DmPin.class,
                Role.class,
                Boolean.class,
                LocalDateTime.class,
                LocalDateTime.class,
                LocalDateTime.class,
                LocalDateTime.class
        );

        constructor.setAccessible(true);

        User user = constructor.newInstance(
                1L,
                new Email("diger@gmail.com"),
                new Username(""),
                new Password(""),
                new Profile(
                        new Nickname("digerDisplayName1"),
                        new Introduce(""),
                        new StaticContentPath(
                                "",
                                "",
                                ""
                        )
                ),
                new PaintPin(""),
                new DmPin(""),
                Role.NORMAL,
                true,
                null,
                null,
                null,
                null
        );

        userJpaRepository.save(user);
    }

    @Test
    @DisplayName("회원가입 정상 로직 테스트")
    public void executePassCase() throws Exception {
        JoinRequest joinRequest = new JoinRequest(
                "diger@gmail.com",
                "digerPassword",
                "digerHashTag",
                null,
                null,
                null,
                null
        );
        executePost(
                "/users/join",
                joinRequest
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 실패 로직 테스트 - 이메일 길이 초과")
    public void executeBrokenCaseBy() {

    }
}
