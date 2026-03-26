package com.openclassroom.mdd.mddauth.services;

import com.openclassroom.mdd.mddauth.dtos.AuthBrowserReqCtx;
import com.openclassroom.mdd.mddauth.dtos.AuthRefreshReq;
import com.openclassroom.mdd.mddauth.dtos.AuthSignRes;
import com.openclassroom.mdd.mddauth.dtos.AuthSigninReq;
import com.openclassroom.mdd.mddauth.dtos.AuthSignupReq;
import com.openclassroom.mdd.mddauth.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final TokenService tokenService;
    private final UserService userService;
    private final SessionService sessionService;
    private final AccountService accountService;

    @Transactional
    public AuthSignRes signup(AuthBrowserReqCtx ctx, AuthSignupReq req)
        throws RuntimeException {
        User user = userService.createUser(req.getUsername(), req.getEmail());
        accountService.createAccountPassword(user, req.getPassword());
        String refreshToken = sessionService.createSession(ctx, user);
        return createAuthResponse(user, refreshToken);
    }

    public AuthSignRes signin(AuthBrowserReqCtx ctx, AuthSigninReq req)
        throws RuntimeException {
        User user = userService.getUserByUsernameOrEmail(
            req.getUsernameOrEmail()
        );
        accountService.loginPassword(user, req.getPassword());
        String refreshToken =
            sessionService.deleteOldSessionsWithSameCtxAndCreate(ctx, user);
        return createAuthResponse(user, refreshToken);
    }

    public AuthSignRes refresh(AuthBrowserReqCtx ctx, AuthRefreshReq req)
        throws RuntimeException {
        String refreshOld = req.getRefreshToken();
        User user = sessionService.getSessionByToken(refreshOld).getUser();
        String refreshNew = sessionService.refreshSession(ctx, refreshOld);
        return createAuthResponse(user, refreshNew);
    }

    private AuthSignRes createAuthResponse(User user, String refresh) {
        return new AuthSignRes(refresh, tokenService.generateAccessToken(user));
    }
}
