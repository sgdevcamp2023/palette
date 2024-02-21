package org.palette.easeluserservice.e2e.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easeluserservice.e2e.AcceptanceTestBase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RetrieveMe extends AcceptanceTestBase {

    @Test
    @DisplayName("유저 정보 조회 성공")
    void executePassCase() throws Exception {
        executeGetWithPassport(
                "/me",
                null
        ).andExpect(status().isOk());
    }
}
