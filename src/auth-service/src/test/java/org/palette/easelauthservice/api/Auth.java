package org.palette.easelauthservice.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easelauthservice.AcceptanceTestBase;
import org.palette.easelauthservice.dto.request.SendEmailAuthRequest;
import org.palette.easelauthservice.redis.RedisEmailAuthService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Auth extends AcceptanceTestBase {

    @Autowired
    private RedisEmailAuthService emailAuthService;

    @BeforeEach
    void createDummyAuthComponent() {
        emailAuthService.create("diger@gmail.com", "testPayload");
    }

    @Test
    @DisplayName("인증번호 입력 성공")
    void success() throws Exception {
        SendEmailAuthRequest sendEmailAuthRequest = new SendEmailAuthRequest(
                "diger@gmail.com",
                "testPayload"
        );

        executePost(
                "/auth",
                sendEmailAuthRequest
        ).andExpect(status().isOk());
    }
}
