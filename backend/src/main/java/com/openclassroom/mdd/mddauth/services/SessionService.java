package com.openclassroom.mdd.mddauth.services;

import com.openclassroom.mdd.mddauth.dtos.AuthBrowserReqCtx;
import com.openclassroom.mdd.mddauth.entities.Session;
import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.exceptions.AuthExceptions;
import com.openclassroom.mdd.mddauth.repositories.SessionRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SessionService {

    private final TokenService tokenService;
    private final SessionRepository sessionRepository;

    public Session getSessionByToken(String refreshToken)
        throws RuntimeException {
        String refreshTokenHash = tokenService.hashRefreshToken(refreshToken);
        return sessionRepository
            .getByTokenHash(refreshTokenHash)
            .orElseThrow(() -> new AuthExceptions.SessionNotFound());
    }

    public String createSession(AuthBrowserReqCtx ctx, User user) {
        String refreshToken = tokenService.generateRefreshToken();
        String refreshTokenHash = tokenService.hashRefreshToken(refreshToken);

        Session session = new Session();
        session.setUser(user);
        session.setIpAddress(ctx.getIpAddress());
        session.setUserAgent(ctx.getUserAgent());
        session.setTokenHash(refreshTokenHash);
        session.setExpiresAt(LocalDateTime.now().plusDays(7));
        sessionRepository.save(session);

        return refreshToken;
    }

    @Transactional
    public String deleteOldSessionsWithSameCtxAndCreate(
        AuthBrowserReqCtx ctx,
        User user
    ) {
        sessionRepository.deleteByUserAndIpAddressAndUserAgent(
            user,
            ctx.getIpAddress(),
            ctx.getUserAgent()
        );
        return createSession(ctx, user);
    }

    /**
     * Get current session from db
     * If session don't exist throw
     * Delete the session
     * If new ctx is suspicious (same token, but different ip or useragent) throw
     * Create new session and return refreshToken
     */
    public String refreshSession(AuthBrowserReqCtx ctx, String refreshToken) {
        Session oldSession = getSessionByToken(refreshToken);

        User user = oldSession.getUser();
        sessionRepository.delete(oldSession);

        if (isSessionSuspicious(ctx, oldSession)) {
            throw new AuthExceptions.SessionSuspicious();
        }

        return createSession(ctx, user);
    }

    private boolean isSessionSuspicious(
        AuthBrowserReqCtx ctx,
        Session session
    ) {
        if (!ctx.getIpAddress().equals(session.getIpAddress())) {
            return true;
        }
        if (!ctx.getUserAgent().equals(session.getUserAgent())) {
            return true;
        }
        return false;
    }
}
