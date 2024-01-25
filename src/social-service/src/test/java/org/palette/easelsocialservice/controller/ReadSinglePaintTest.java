package org.palette.easelsocialservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.palette.easelsocialservice.AcceptanceTestBase;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReadSinglePaintTest extends AcceptanceTestBase {
    @Test
    @DisplayName("페인트 단일 조회 로직 테스트")
    void executePassCase() throws Exception {
        executeGet(
                "/paints/" + 12L
        ).andExpect(status().is2xxSuccessful());

    }
}
