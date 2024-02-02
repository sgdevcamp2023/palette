package org.palette.easelsocialservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easelsocialservice.AcceptanceTestBase;
import org.palette.easelsocialservice.dto.request.FollowUserRequest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookmarkTest extends AcceptanceTestBase {

    @Test
    @DisplayName("사용자 북마크 정상 로직 테스트")
    void executePassCase() throws Exception {

        executePost(
                "/users/mark/1",
                null
        ).andExpect(status().is2xxSuccessful());
    }
}
