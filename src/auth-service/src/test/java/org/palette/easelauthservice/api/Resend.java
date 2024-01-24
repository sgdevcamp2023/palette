package org.palette.easelauthservice.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easelauthservice.AcceptanceTestBase;
import org.palette.easelauthservice.dto.request.AuthEmailResendRequest;
import org.palette.easelauthservice.redis.RedisEmailAuthService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Resend extends AcceptanceTestBase {

    @Autowired
    private RedisEmailAuthService emailAuthService;

    @BeforeEach
    void createDummyAuthComponent() {
        emailAuthService.create("kitten.diger@gmail.com", "testPayload");
    }

    @Test
    @DisplayName("인증번호 재발송 성공")
    void success() throws Exception {
        AuthEmailResendRequest request = new AuthEmailResendRequest(
                "kitten.diger@gmail.com"
        );

        executePost(
                "/auth/re-send",
                request
        ).andExpect(status().isOk());
    }
}
