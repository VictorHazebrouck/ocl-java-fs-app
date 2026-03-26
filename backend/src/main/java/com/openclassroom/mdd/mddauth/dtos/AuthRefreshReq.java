package com.openclassroom.mdd.mddauth.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AuthRefreshReq {

    @NotBlank
    String refreshToken;
}
