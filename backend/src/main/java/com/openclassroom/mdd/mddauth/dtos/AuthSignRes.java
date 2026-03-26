package com.openclassroom.mdd.mddauth.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AuthSignRes {

    @NotBlank
    String refreshToken;

    @NotBlank
    String accessToken;
}
