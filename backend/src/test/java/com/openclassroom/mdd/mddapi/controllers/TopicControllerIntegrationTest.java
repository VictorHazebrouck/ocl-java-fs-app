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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class TopicControllerIntegrationTest {

    private static final String PASSWORD = "password";
    private static final String USER_AGENT = "TEST_AGENT";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should get all topics")
    void should_get_all_topics() throws Exception {
        AuthSignRes tokens = signupAndGetTokens();

        mockMvc
            .perform(
                get("/api/topic")
                    .header(
                        "Authorization",
                        "Bearer " + tokens.getAccessToken()
                    )
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName(
        "Should get topics with subscription status when authenticated"
    )
    void should_get_topics_with_subscription() throws Exception {
        AuthSignRes tokens = signupAndGetTokens();

        mockMvc
            .perform(
                get("/api/topic/with-am-i-subscribed")
                    .header(
                        "Authorization",
                        "Bearer " + tokens.getAccessToken()
                    )
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Should get only subscribed topics when authenticated")
    void should_get_only_subscribed_topics() throws Exception {
        AuthSignRes tokens = signupAndGetTokens();

        mockMvc
            .perform(
                get("/api/topic/only-subscribed")
                    .header(
                        "Authorization",
                        "Bearer " + tokens.getAccessToken()
                    )
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName(
        "Should return 401 when no token provided for protected endpoints"
    )
    void should_fail_without_token() throws Exception {
        mockMvc
            .perform(get("/api/topic/with-am-i-subscribed"))
            .andExpect(status().isUnauthorized());

        mockMvc
            .perform(get("/api/topic/only-subscribed"))
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
                    .contentType("application/json")
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
