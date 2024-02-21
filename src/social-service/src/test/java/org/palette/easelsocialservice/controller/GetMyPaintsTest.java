package org.palette.easelsocialservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easelsocialservice.AcceptanceTestBase;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GetMyPaintsTest extends AcceptanceTestBase {
    @Test
    @DisplayName("사용자 페이지 내 페인트 모두 조회 테스트")
    void executePassCase() throws Exception {
        executeGet("/users/" + 100 + "/paint")
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(anything())))
                .andExpect(jsonPath("$[0].isReply", is(anything())))
                .andExpect(jsonPath("$[0].authorId", is(anything())))
                .andExpect(jsonPath("$[0].authorUsername", is(anything())))
                .andExpect(jsonPath("$[0].authorNickname", is(anything())))
                .andExpect(jsonPath("$[0].authorImagePath", is(anything())))
                .andExpect(jsonPath("$[0].authorStatus", is(anything())))
                .andExpect(jsonPath("$[0].quotePaint", is(anything())))
                .andExpect(jsonPath("$[0].createdAt", is(anything())))
                .andExpect(jsonPath("$[0].text", is(anything())))
                .andExpect(jsonPath("$[0].replyCount", is(anything())))
                .andExpect(jsonPath("$[0].likeCount", is(anything())))
                .andExpect(jsonPath("$[0].like", is(anything())))
                .andExpect(jsonPath("$[0].repainted", is(anything())))
                .andExpect(jsonPath("$[0].marked", is(anything())))
                .andExpect(jsonPath("$[0].views", is(anything())))
                .andExpect(jsonPath("$[0].entities", is(anything())))
                .andExpect(jsonPath("$[0].includes", is(anything())))
        ;
    }
}
