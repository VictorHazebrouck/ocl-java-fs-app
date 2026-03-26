package com.openclassroom.mdd.mddauth.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AuthBrowserReqCtx {

    @NotNull // maybe not rgpd or something ?
    String ipAddress;

    @NotBlank
    String userAgent;
}
