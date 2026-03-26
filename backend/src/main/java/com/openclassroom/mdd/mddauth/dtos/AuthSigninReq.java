package com.openclassroom.mdd.mddauth.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AuthSigninReq {

    @NotBlank
    String usernameOrEmail;

    @NotBlank
    String password;
}
