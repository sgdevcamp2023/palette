package org.palette.easeluserservice.e2e.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easeluserservice.dto.request.TemporaryJoinRequest;
import org.palette.easeluserservice.e2e.AcceptanceTestBase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TemporaryJoin extends AcceptanceTestBase {

    @Test
    @DisplayName("임시 회원가입 정상 로직 테스트")
    public void executePassCase() throws Exception {
        TemporaryJoinRequest temporaryJoinRequest = new TemporaryJoinRequest(
                "diger@gmail.com",
                "digerDisplayName"
        );
        executePost(
                "/users/temporary-join",
                temporaryJoinRequest
        ).andExpect(status().isCreated());
    }
}
