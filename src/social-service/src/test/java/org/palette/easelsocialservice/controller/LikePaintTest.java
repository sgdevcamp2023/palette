package org.palette.easelsocialservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easelsocialservice.AcceptanceTestBase;
import org.palette.easelsocialservice.dto.request.*;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikePaintTest extends AcceptanceTestBase {
    @Test
    @DisplayName("페인트 좋아요 정상 로직 테스트")
    void executePassCase() throws Exception {
        LikePaintRequest likePaintRequest = new LikePaintRequest(12L);

        executePost(
                "/users/" + 103L + "/like",
                likePaintRequest
        ).andExpect(status().is2xxSuccessful());
    }
}
