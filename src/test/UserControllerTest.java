package com.baeldung.validation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenInvalidInput_thenReturnsValidationErrors() throws Exception {
        String payload = """
            {
                "name": "",
                "email": "invalid-email",
                "age": 15
            }
        """;

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0]").value("Name must not be blank."))
            .andExpect(jsonPath("$[1]").value("Please provide a valid email address."))
            .andExpect(jsonPath("$[2]").value("Age must be at least 18."));
    }
}