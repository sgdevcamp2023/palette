package org.palette.easeluserservice.e2e.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easeluserservice.api.dto.request.JoinRequest;
import org.palette.easeluserservice.e2e.AcceptanceTestBase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class Join extends AcceptanceTestBase {

    @Test
    @DisplayName("회원가입 정상 로직 테스트")
    public void executePassCase() throws Exception {
        JoinRequest joinRequest = new JoinRequest(
                "diger@gmail.com",
                "digerPassword",
                "digerHashTag",
                "digerDisplayname",
                null,
                null,
                null,
                null
        );
        executePost(
                "/api/v1/users/join",
                joinRequest
        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 실패 로직 테스트 - 이메일 길이 초과")
    public void executeBrokenCaseBy() {

    }
}
