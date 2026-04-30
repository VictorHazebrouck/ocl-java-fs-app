package com.openclassroom.mdd.mddauth.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddauth.dtos.*;
import com.openclassroom.mdd.mddauth.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    private TokenService tokenService;
    private UserService userService;
    private SessionService sessionService;
    private AccountService accountService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        tokenService = mock(TokenService.class);
        userService = mock(UserService.class);
        sessionService = mock(SessionService.class);
        accountService = mock(AccountService.class);

        authService = new AuthService(
            tokenService,
            userService,
            sessionService,
            accountService
        );
    }

    @Test
    void shouldSignupUser() {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx("127.0.0.1", "JUnit");

        AuthSignupReq req = new AuthSignupReq("john@mail.com", "john", "pass");

        User user = new User();
        user.setId(1L);
        user.setUsername("john");

        when(userService.createUser("john", "john@mail.com")).thenReturn(user);

        when(sessionService.createSession(ctx, user)).thenReturn(
            "refresh-token"
        );

        when(tokenService.generateAccessToken(user)).thenReturn("access-token");

        AuthSignRes res = authService.signup(ctx, req);

        assertNotNull(res);
        assertEquals("refresh-token", res.getRefreshToken());
        assertEquals("access-token", res.getAccessToken());

        verify(accountService).createAccountPassword(user, "pass");
    }

    @Test
    void shouldSigninUser() {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx("127.0.0.1", "JUnit");

        AuthSigninReq req = new AuthSigninReq("john", "pass");

        User user = new User();
        user.setId(1L);
        user.setUsername("john");

        when(userService.getUserByUsernameOrEmail("john")).thenReturn(user);

        when(
            sessionService.deleteOldSessionsWithSameCtxAndCreate(ctx, user)
        ).thenReturn("refresh-token");

        when(tokenService.generateAccessToken(user)).thenReturn("access-token");

        AuthSignRes res = authService.signin(ctx, req);

        assertNotNull(res);
        assertEquals("refresh-token", res.getRefreshToken());
        assertEquals("access-token", res.getAccessToken());

        verify(accountService).loginPassword(user, "pass");
    }

    @Test
    void shouldRefreshSession() {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx("127.0.0.1", "JUnit");

        AuthRefreshReq req = new AuthRefreshReq("old-token");

        User user = new User();
        user.setId(1L);
        user.setUsername("john");

        com.openclassroom.mdd.mddauth.entities.Session session =
            new com.openclassroom.mdd.mddauth.entities.Session();

        session.setUser(user);

        when(sessionService.getSessionByToken("old-token")).thenReturn(session);

        when(sessionService.refreshSession(ctx, "old-token")).thenReturn(
            "new-refresh-token"
        );

        when(tokenService.generateAccessToken(user)).thenReturn("access-token");

        AuthSignRes res = authService.refresh(ctx, req);

        assertNotNull(res);
        assertEquals("new-refresh-token", res.getRefreshToken());
        assertEquals("access-token", res.getAccessToken());
    }
}
