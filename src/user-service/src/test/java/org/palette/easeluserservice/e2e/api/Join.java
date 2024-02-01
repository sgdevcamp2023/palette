package org.palette.easeluserservice.e2e.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easeluserservice.dto.request.JoinRequest;
import org.palette.easeluserservice.e2e.AcceptanceTestBase;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.persistence.UserJpaRepository;
import org.palette.easeluserservice.persistence.embed.Password;
import org.palette.easeluserservice.persistence.embed.Pin;
import org.palette.easeluserservice.persistence.embed.Profile;
import org.palette.easeluserservice.persistence.embed.StaticContentPath;
import org.palette.easeluserservice.persistence.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Join extends AcceptanceTestBase {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void insertTemporaryUser() throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        final User user = createMockUser();
        userJpaRepository.save(user);
    }

    @Test
    @DisplayName("회원가입 정상 로직 테스트")
    void executePassCase() throws Exception {
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
