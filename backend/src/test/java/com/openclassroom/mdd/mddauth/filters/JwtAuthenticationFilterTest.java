package com.openclassroom.mdd.mddauth.filters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddauth.dtos.UserDto;
import com.openclassroom.mdd.mddauth.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

class JwtAuthenticationFilterTest {

    private TokenService tokenService;
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        tokenService = mock(TokenService.class);
        filter = new JwtAuthenticationFilter(tokenService);

        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSkipAuthEndpoints() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getServletPath()).thenReturn("/auth/signin");

        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verifyNoInteractions(tokenService);
    }

    @Test
    void shouldRejectWhenNoAuthorizationHeader() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getServletPath()).thenReturn("/articles");

        when(req.getHeader("Authorization")).thenReturn(null);

        HttpServletResponse res = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(res.getWriter()).thenReturn(writer);

        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(req, res, chain);

        verify(res).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer).write("Missing or invalid Authorization header");
        verifyNoInteractions(chain);
    }

    @Test
    void shouldAuthenticateValidToken() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getServletPath()).thenReturn("/articles");
        when(req.getHeader("Authorization")).thenReturn("Bearer valid-token");

        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        UserDto dto = new UserDto(
            1L,
            "john",
            "john@mail.com",
            true,
            java.time.LocalDateTime.now(),
            java.time.LocalDateTime.now()
        );

        when(tokenService.decodeAccessToken("valid-token")).thenReturn(dto);

        filter.doFilter(req, res, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(req, res);
    }
}
