package com.openclassroom.mdd.mddapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class TopicAmISubscribedDto {

    @NotNull
    Long id;

    @NotBlank
    String name;

    @NotBlank
    String description;

    @NotNull
    Boolean amISubscribed;

    @NotNull
    LocalDateTime createdAt;

    @NotNull
    LocalDateTime updatedAt;
}
