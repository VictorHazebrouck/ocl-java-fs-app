package com.openclassroom.mdd.mddauth.controllers;

import com.openclassroom.mdd.mddauth.dtos.AuthBrowserReqCtx;
import com.openclassroom.mdd.mddauth.dtos.AuthRefreshReq;
import com.openclassroom.mdd.mddauth.dtos.AuthSetPasswordReq;
import com.openclassroom.mdd.mddauth.dtos.AuthSetUsernameReq;
import com.openclassroom.mdd.mddauth.dtos.AuthSignRes;
import com.openclassroom.mdd.mddauth.dtos.AuthSigninReq;
import com.openclassroom.mdd.mddauth.dtos.AuthSignupReq;
import com.openclassroom.mdd.mddauth.dtos.UserDto;
import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.mappers.UserMapper;
import com.openclassroom.mdd.mddauth.services.AccountService;
import com.openclassroom.mdd.mddauth.services.AuthService;
import com.openclassroom.mdd.mddauth.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;
    private final UserService userService;

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

    @PostMapping("/auth/password")
    public ResponseEntity<Boolean> password(
        @Valid @RequestBody AuthSetPasswordReq req,
        HttpServletRequest reqCtx
    ) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        accountService.setAccountPasword(user, req.getNewPassword());

        return ResponseEntity.ok(true);
    }

    @PostMapping("/auth/username")
    public ResponseEntity<Boolean> username(
        @Valid @RequestBody AuthSetUsernameReq req,
        HttpServletRequest reqCtx
    ) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        userService.setUsername(user, req.getNewUsername());

        return ResponseEntity.ok(true);
    }

    @GetMapping("/auth/user")
    public ResponseEntity<UserDto> user(HttpServletRequest reqCtx) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        return ResponseEntity.ok(
            UserMapper.INSTANCE.toDto(userService.getUser(user))
        );
    }
}
