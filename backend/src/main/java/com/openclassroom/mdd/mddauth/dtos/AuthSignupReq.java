package com.openclassroom.mdd.mddauth.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AuthSignupReq {

    @NotBlank
    String email;

    @NotBlank
    String username;

    @NotBlank
    String password;
}
