package org.palette.easelsocialservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// @Transactional
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
                                    .header("Authorization", "eyJ1c2VySW5mbyI6eyJpZCI6MTAwLCJlbWFpbCI6ImNvdXJ0bmV5QHRlc3QuY29tIiwibmlja25hbWUiOiLsvZTtirjri4giLCJ1c2VybmFtZSI6ImNvdXJ0bmV5Iiwicm9sZSI6Ik5PUk1BTCIsImlzQWN0aXZhdGVkIjp0cnVlLCJhY2Nlc3NlZEF0IjoiMjAyNC0wMi0wMlQxMToyNDo1Ny41MzU3NTgiLCJjcmVhdGVkQXQiOiIyMDI0LTAyLTAyVDExOjI0OjU3LjUzNTk4NyIsImRlbGV0ZWRBdCI6bnVsbH0sImludGVncml0eUtleSI6InhVbXFGU09MVzRTVGRpVjBtVk43ZXVvT2RxTjZwOXhEbWo3NVdUSTZ3a2c9In0=")
                                    .content(objectMapper.writeValueAsString(requestDto))
                                    .contentType(APPLICATION_JSON)
                                    .accept(APPLICATION_JSON))
                    .andDo(print());
        } catch (Exception e) {
            throw new BuildResultActionsException(e.getCause());
        }
    }

    public ResultActions executeGet(
            final String url
    ) {

        try {
            return mvc.perform(
                            get(url)
                                    .contentType(APPLICATION_JSON)
                                    .accept(APPLICATION_JSON))
                    .andDo(print());
        } catch (Exception e) {
            throw new BuildResultActionsException(e.getCause());
        }
    }
}
