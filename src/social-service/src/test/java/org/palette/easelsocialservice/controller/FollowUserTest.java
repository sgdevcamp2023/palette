package org.palette.easelsocialservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easelsocialservice.AcceptanceTestBase;
import org.palette.easelsocialservice.dto.request.FollowUserRequest;
import org.palette.easelsocialservice.dto.request.LikePaintRequest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FollowUserTest extends AcceptanceTestBase {
    @Test
    @DisplayName("사용자 팔로우 정상 로직 테스트")
    void executePassCase() throws Exception {
        FollowUserRequest followUserRequest = new FollowUserRequest(103L);

        executePost(
                "/users/" + 107 + "/follow",
                followUserRequest
        ).andExpect(status().is2xxSuccessful());
    }
}
