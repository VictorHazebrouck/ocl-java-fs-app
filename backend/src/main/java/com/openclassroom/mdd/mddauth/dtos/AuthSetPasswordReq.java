package com.openclassroom.mdd.mddauth.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class AuthSetPasswordReq {

    @NotBlank
    String newPassword;
}
