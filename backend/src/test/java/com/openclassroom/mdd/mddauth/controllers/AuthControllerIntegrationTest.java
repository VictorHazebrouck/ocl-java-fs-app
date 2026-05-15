package com.openclassroom.mdd.mddauth.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroom.mdd.mddauth.dtos.*;
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
@AutoConfigureMockMvc
@Transactional
class AuthControllerIntegrationTest {

    private static final String PASSWORD = "password";
    private static final String USER_AGENT = "TEST_AGENT";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Signup should return tokens")
    void signup_should_return_tokens() throws Exception {
        AuthSignRes res = signup();

        assertNotNull(res.getAccessToken());
        assertNotNull(res.getRefreshToken());
    }

    @Test
    @DisplayName("Signin should return tokens")
    void signin_should_return_tokens() throws Exception {
        Credentials credentials = createUser();

        AuthSignRes res = signin(credentials.email(), credentials.password());

        assertNotNull(res.getAccessToken());
        assertNotNull(res.getRefreshToken());
    }

    @Test
    @DisplayName(
        "Refresh should return tokens (may or may not rotate access token)"
    )
    void refresh_should_return_tokens() throws Exception {
        AuthSignRes signup = signup();

        AuthSignRes refreshed = refresh(signup.getRefreshToken());

        assertNotNull(refreshed.getAccessToken());
        assertNotNull(refreshed.getRefreshToken());

        // DO NOT assume rotation unless explicitly implemented
        assertEquals(
            signup.getAccessToken().getClass(),
            refreshed.getAccessToken().getClass()
        );
    }

    @Test
    @DisplayName("User endpoint should return authenticated user")
    void user_should_return_authenticated_user() throws Exception {
        AuthSignRes tokens = signup();

        mockMvc
            .perform(
                get("/auth/user")
                    .header(
                        "Authorization",
                        "Bearer " + tokens.getAccessToken()
                    )
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").exists())
            .andExpect(jsonPath("$.email").exists());
    }

    @Test
    @DisplayName("Signin with wrong password should return 401")
    void signin_should_fail_with_wrong_password() throws Exception {
        Credentials credentials = createUser();

        AuthSigninReq req = new AuthSigninReq(
            credentials.email(),
            "wrong-password"
        );

        mockMvc
            .perform(
                post("/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Refresh with invalid token should return 401")
    void refresh_should_fail_with_invalid_token() throws Exception {
        AuthRefreshReq req = new AuthRefreshReq("invalid-token");

        mockMvc
            .perform(
                post("/auth/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("User endpoint should require authentication (401 expected)")
    void user_should_fail_without_token() throws Exception {
        mockMvc
            .perform(get("/auth/user").header("User-Agent", USER_AGENT))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Password endpoint should update password")
    void password_should_update_password() throws Exception {
        Credentials credentials = createUser();

        AuthSignRes tokens = signin(
            credentials.email(),
            credentials.password()
        );

        String newPassword = "new-password-123";

        AuthSetPasswordReq req = new AuthSetPasswordReq(newPassword);

        mockMvc
            .perform(
                post("/auth/password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
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
    @DisplayName("Username endpoint should update username")
    void username_should_update_username() throws Exception {
        Credentials credentials = createUser();

        AuthSignRes tokens = signin(
            credentials.email(),
            credentials.password()
        );

        String newUsername = "updated_username";

        AuthSetUsernameReq req = new AuthSetUsernameReq(newUsername);

        mockMvc
            .perform(
                post("/auth/username")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .header(
                        "Authorization",
                        "Bearer " + tokens.getAccessToken()
                    )
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andExpect(content().string("true"));

        mockMvc
            .perform(
                get("/auth/user")
                    .header(
                        "Authorization",
                        "Bearer " + tokens.getAccessToken()
                    )
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(newUsername));
    }

    // ---------------- helpers ----------------

    private AuthSignRes signup() throws Exception {
        Credentials c = createUser();
        return signin(c.email(), c.password());
    }

    private Credentials createUser() throws Exception {
        String id = UUID.randomUUID().toString().substring(0, 8);

        String email = id + "@test.com";
        String username = "user_" + id;

        AuthSignupReq req = new AuthSignupReq(email, username, PASSWORD);

        mockMvc
            .perform(
                post("/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").exists())
            .andExpect(jsonPath("$.refreshToken").exists());

        return new Credentials(email, PASSWORD);
    }

    private AuthSignRes signin(String email, String password) throws Exception {
        AuthSigninReq req = new AuthSigninReq(email, password);

        String json = mockMvc
            .perform(
                post("/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return parse(json);
    }

    private AuthSignRes refresh(String refreshToken) throws Exception {
        AuthRefreshReq req = new AuthRefreshReq(refreshToken);

        String json = mockMvc
            .perform(
                post("/auth/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
                    .header("User-Agent", USER_AGENT)
            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return parse(json);
    }

    private AuthSignRes parse(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);

        return new AuthSignRes(
            node.get("refreshToken").asText(),
            node.get("accessToken").asText()
        );
    }

    private record Credentials(String email, String password) {}
}
