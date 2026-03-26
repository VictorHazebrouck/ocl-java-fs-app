package com.openclassroom.mdd.mddauth.controllers;

import com.openclassroom.mdd.mddauth.dtos.AuthBrowserReqCtx;
import com.openclassroom.mdd.mddauth.dtos.AuthRefreshReq;
import com.openclassroom.mdd.mddauth.dtos.AuthSignRes;
import com.openclassroom.mdd.mddauth.dtos.AuthSigninReq;
import com.openclassroom.mdd.mddauth.dtos.AuthSignupReq;
import com.openclassroom.mdd.mddauth.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthSignRes> signup(
        @Valid @RequestBody AuthSignupReq req,
        HttpServletRequest reqCtx
    ) {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx(
            reqCtx.getRemoteAddr(),
            reqCtx.getHeader("User-Agent")
        );
        AuthSignRes res = authService.signup(ctx, req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<AuthSignRes> signin(
        @Valid @RequestBody AuthSigninReq req,
        HttpServletRequest reqCtx
    ) {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx(
            reqCtx.getRemoteAddr(),
            reqCtx.getHeader("User-Agent")
        );

        AuthSignRes res = authService.signin(ctx, req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthSignRes> refresh(
        @Valid @RequestBody AuthRefreshReq req,
        HttpServletRequest reqCtx
    ) {
        AuthBrowserReqCtx ctx = new AuthBrowserReqCtx(
            reqCtx.getRemoteAddr(),
            reqCtx.getHeader("User-Agent")
        );

        AuthSignRes res = authService.refresh(ctx, req);
        return ResponseEntity.ok(res);
    }
}
