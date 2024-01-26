package org.palette.easelauthservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@Transactional
@Disabled
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AcceptanceTestBase {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    public ResultActions executePost(
            final String url,
            final Object requestDto
    ) {
        try {
            return mvc.perform(
                            post(url)
                                    .content(objectMapper.writeValueAsString(requestDto))
                                    .contentType(APPLICATION_JSON)
                                    .accept(APPLICATION_JSON))
                    .andDo(print());
        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }
}
