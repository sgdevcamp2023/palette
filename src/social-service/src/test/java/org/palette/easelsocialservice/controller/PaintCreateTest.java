package org.palette.easelsocialservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easelsocialservice.AcceptanceTestBase;
import org.palette.easelsocialservice.dto.request.*;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaintCreateTest extends AcceptanceTestBase {
    @Test
    @DisplayName("페인트 생성 정상 로직 테스트")
    void executePassCase() throws Exception {
        PaintCreateRequest paintCreateRequest = new PaintCreateRequest(
                "Learn how to use the user Tweet timeline and user mention timeline endpoints in the X API v2 to explore Tweet...",
                Optional.of(Arrays.asList(
                        new MediaRequest("/67674912/ttt.png", "image"),
                        new MediaRequest("/6dddttt12/ttt.png", "image")
                )),
                Optional.of(Arrays.asList(103L, 108L)),
                Optional.empty(),
                Optional.empty(),
                Optional.of(Arrays.asList(
                        new HashtagRequest(7, 9, "yyyy"),
                        new HashtagRequest(4, 7, "hapggggggpy")
                )),
                Optional.of(Arrays.asList(
                        new MentionRequest(0, 3, 101L, "wonyoung"),
                        new MentionRequest(4, 7, 102L, "bella")
                )),
                Optional.of(Arrays.asList(
                        new LinkRequest(0, 3, "http://example.com"),
                        new LinkRequest(4, 7, "http://anotherexample.com")
                ))
        );

        executePost(
                "/paints",
                paintCreateRequest
        ).andExpect(status().isCreated());
    }
}
