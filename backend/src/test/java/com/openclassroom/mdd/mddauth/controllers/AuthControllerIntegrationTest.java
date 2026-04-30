package com.openclassroom.mdd.mddauth.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.openclassroom.mdd.mddauth.dtos.AuthSignRes;
import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.services.AccountService;
import com.openclassroom.mdd.mddauth.services.AuthService;
import com.openclassroom.mdd.mddauth.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private AccountService accountService;

    @MockitoBean
    private UserService userService;

    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void signup_should_return_tokens() throws Exception {
        AuthSignRes res = new AuthSignRes("refresh", "access");

        when(authService.signup(any(), any())).thenReturn(res);

        mockMvc
            .perform(
                post("/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                            {
                                "username": "john",
                                "email": "john@mail.com",
                                "password": "pass"
                            }
                        """
                    )
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.refreshToken").value("refresh"))
            .andExpect(jsonPath("$.accessToken").value("access"));
    }

    @Test
    void signin_should_return_tokens() throws Exception {
        AuthSignRes res = new AuthSignRes("refresh", "access");

        when(authService.signin(any(), any())).thenReturn(res);

        mockMvc
            .perform(
                post("/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                            {
                                "usernameOrEmail": "john",
                                "password": "pass"
                            }
                        """
                    )
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.refreshToken").value("refresh"));
    }

    @Test
    void refresh_should_return_tokens() throws Exception {
        AuthSignRes res = new AuthSignRes("new-refresh", "access");

        when(authService.refresh(any(), any())).thenReturn(res);

        mockMvc
            .perform(
                post("/auth/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """
                            {
                                "refreshToken": "old-token"
                            }
                        """
                    )
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.refreshToken").value("new-refresh"));
    }

    @Test
    void user_should_return_user() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
            )
        );

        when(userService.getUser(user)).thenReturn(user);

        mockMvc.perform(get("/auth/user")).andExpect(status().isOk());
    }
}
