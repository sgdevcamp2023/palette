package org.palette.easeluserservice.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.palette.easeluserservice.persistence.User;
import org.palette.easeluserservice.persistence.UserJpaRepository;
import org.palette.easeluserservice.persistence.embed.Password;
import org.palette.easeluserservice.persistence.embed.Pin;
import org.palette.easeluserservice.persistence.embed.Profile;
import org.palette.easeluserservice.persistence.embed.StaticContentPath;
import org.palette.easeluserservice.persistence.enums.Role;
import org.palette.passport.PassportGenerator;
import org.palette.passport.component.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Transactional
@Disabled
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AcceptanceTestBase {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PassportGenerator passportGenerator;

    public ResultActions executeGetWithPassport(
            final String url,
            final Object pathVariable
    ) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> userClass = User.class;
        Constructor<User> constructor = userClass.getDeclaredConstructor(
                Long.class,
                String.class,
                String.class,
                Password.class,
                Profile.class,
                Pin.class,
                Role.class,
                Boolean.class,
                Boolean.class,
                LocalDateTime.class,
                LocalDateTime.class,
                LocalDateTime.class,
                LocalDateTime.class
        );

        constructor.setAccessible(true);

        User user = constructor.newInstance(
                1L,
                "kitten.diger@gmail.com",
                "digerUsername",
                new Password("", passwordEncoder),
                new Profile(
                        "digerDisplayNickName",
                        "digerIntroduce",
                        new StaticContentPath(
                                "",
                                "",
                                ""
                        )
                ),
                new Pin("", ""),
                Role.NORMAL,
                true,
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );

        userJpaRepository.save(user);

        final String passportPayload = passportGenerator.generatePassport(
                new UserInfo(
                        user.getId(),
                        user.getEmail(),
                        user.getProfile().nickname(),
                        user.getUsername(),
                        user.getRole().toString(),
                        user.getIsActivated(),
                        user.getAccessedAt().toString(),
                        user.getCreatedAt().toString(),
                        null
                ));

        try {
            return mvc.perform(
                            get(url)
                                    .header("Authorization", passportPayload)
                                    .contentType(APPLICATION_JSON)
                                    .accept(APPLICATION_JSON))
                    .andDo(print());
        } catch (Exception e) {
            throw new BuildResultActionsException(e.getCause());
        }
    }

    public ResultActions executePost(
            final String url,
            final Object requestDto
    ) {

        try {
            return mvc.perform(
                            post(url)
                                    .content(objectMapper.writeValueAsString(requestDto))
                                    .contentType(APPLICATION_JSON)
                                    .accept(APPLICATION_JSON))
                    .andDo(print());
        } catch (Exception e) {
            throw new BuildResultActionsException(e.getCause());
        }
    }
}
