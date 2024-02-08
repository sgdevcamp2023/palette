package org.palette.easeltimelineservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easeltimelineservice.AcceptanceTestBase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetFollowingTimelineTest extends AcceptanceTestBase {
    @Test
    @DisplayName("팔로잉 타임라인 조회 정상 로직 테스트")
    void executePassCase() throws Exception {
        executeGet(
                "/timeline/following" + "?page=0&size=20"
        ).andExpect(status().is2xxSuccessful());
    }
}

