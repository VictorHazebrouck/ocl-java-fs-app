package com.openclassroom.mdd.mddapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.mdd.mddauth.dtos.AuthSignRes;
import com.openclassroom.mdd.mddauth.dtos.AuthSignupReq;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class SubscriptionControllerIntegrationTest {

    private static final String PASSWORD = "password";
    private static final String USER_AGENT = "TEST_AGENT";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should subscribe to topic when authenticated")
    void should_subscribe_to_topic() throws Exception {
        AuthSignRes tokens = signupAndGetTokens();

        mockMvc
            .perform(
                post("/api/subscription/1")
                    .header(
                        "Authorization",
                        "Bearer " + tokens.getAccessToken()
                    )
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Should unsubscribe from topic when authenticated")
    void should_unsubscribe_from_topic() throws Exception {
        AuthSignRes tokens = signupAndGetTokens();

        mockMvc
            .perform(
                delete("/api/subscription/1")
                    .header(
                        "Authorization",
                        "Bearer " + tokens.getAccessToken()
                    )
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 401 when no token provided")
    void should_fail_without_token() throws Exception {
        mockMvc
            .perform(post("/api/subscription/1"))
            .andExpect(status().isUnauthorized());
    }

    private AuthSignRes signupAndGetTokens() throws Exception {
        String id = UUID.randomUUID().toString().substring(0, 8);

        String email = id + "@test.com";
        String username = "user_" + id;

        AuthSignupReq req = new AuthSignupReq(email, username, PASSWORD);

        String response = mockMvc
            .perform(
                post("/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        JsonNode node = objectMapper.readTree(response);

        return new AuthSignRes(
            node.get("refreshToken").asText(),
            node.get("accessToken").asText()
        );
    }
}
