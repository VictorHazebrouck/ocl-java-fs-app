package com.openclassroom.mdd.mddauth.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddauth.dtos.AuthBrowserReqCtx;
import com.openclassroom.mdd.mddauth.entities.Session;
import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.repositories.SessionRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    // ------------------------
    // getSessionByToken
    // ------------------------

    @Test
    void should_return_session_by_refresh_token() {
        when(tokenService.hashRefreshToken("token")).thenReturn("hashed");

        Session session = new Session();
        when(sessionRepository.getByTokenHash("hashed")).thenReturn(
            Optional.of(session)
        );

        Session result = sessionService.getSessionByToken("token");

        assertNotNull(result);
        verify(sessionRepository).getByTokenHash("hashed");
    }

    @Test
    void should_throw_when_session_not_found() {
        when(tokenService.hashRefreshToken("token")).thenReturn("hashed");

        when(sessionRepository.getByTokenHash("hashed")).thenReturn(
            Optional.empty()
        );

        assertThrows(BadCredentialsException.class, () ->
            sessionService.getSessionByToken("token")
        );
    }

    // ------------------------
    // createSession
    // ------------------------

    @Test
    void should_create_session_and_return_refresh_token() {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx("127.0.0.1", "JUnit");

        User user = new User();
        user.setId(1L);

        when(tokenService.generateRefreshToken()).thenReturn("refresh");

        when(tokenService.hashRefreshToken("refresh")).thenReturn("hashed");

        Session resultSession = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(
            resultSession
        );

        String result = sessionService.createSession(ctx, user);

        assertEquals("refresh", result);
        verify(sessionRepository).save(any(Session.class));
    }

    // ------------------------
    // deleteOldSessionsWithSameCtxAndCreate
    // ------------------------

    @Test
    void should_delete_old_sessions_and_create_new_one() {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx("ip", "ua");

        User user = new User();

        doNothing()
            .when(sessionRepository)
            .deleteByUserAndIpAddressAndUserAgent(user, "ip", "ua");

        when(tokenService.generateRefreshToken()).thenReturn("refresh");

        when(tokenService.hashRefreshToken("refresh")).thenReturn("hashed");

        when(sessionRepository.save(any())).thenReturn(new Session());

        String result = sessionService.deleteOldSessionsWithSameCtxAndCreate(
            ctx,
            user
        );

        assertEquals("refresh", result);
        verify(sessionRepository).deleteByUserAndIpAddressAndUserAgent(
            user,
            "ip",
            "ua"
        );
    }

    // ------------------------
    // refreshSession (normal case)
    // ------------------------

    @Test
    void should_refresh_session_successfully() {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx("ip", "ua");

        User user = new User();

        Session oldSession = new Session();
        oldSession.setUser(user);
        oldSession.setIpAddress("ip");
        oldSession.setUserAgent("ua");

        when(tokenService.hashRefreshToken("token")).thenReturn("hashed");

        when(sessionRepository.getByTokenHash("hashed")).thenReturn(
            Optional.of(oldSession)
        );

        when(tokenService.generateRefreshToken()).thenReturn("newToken");

        when(tokenService.hashRefreshToken("newToken")).thenReturn("newHashed");

        when(sessionRepository.save(any())).thenReturn(new Session());

        String result = sessionService.refreshSession(ctx, "token");

        assertEquals("newToken", result);
        verify(sessionRepository).delete(oldSession);
    }

    // ------------------------
    // refreshSession (suspicious case)
    // ------------------------

    @Test
    void should_throw_when_session_is_suspicious() {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx("DIFFERENT_IP", "ua");

        User user = new User();

        Session oldSession = new Session();
        oldSession.setUser(user);
        oldSession.setIpAddress("ip");
        oldSession.setUserAgent("ua");

        when(tokenService.hashRefreshToken("token")).thenReturn("hashed");

        when(sessionRepository.getByTokenHash("hashed")).thenReturn(
            Optional.of(oldSession)
        );

        doNothing().when(sessionRepository).delete(oldSession);

        assertThrows(BadCredentialsException.class, () ->
            sessionService.refreshSession(ctx, "token")
        );
    }
}
