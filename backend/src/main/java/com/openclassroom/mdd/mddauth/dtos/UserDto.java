package com.openclassroom.mdd.mddauth.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class UserDto {

    @NotNull
    Long id;

    @NotBlank
    String username;

    @NotBlank
    String email;

    @NotBlank
    Boolean emailVerified;

    @NotBlank
    LocalDateTime createdAt;

    @NotBlank
    LocalDateTime updatedAt;
}
