package com.openclassroom.mdd.mddapi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class TopicCreateReq {

    @NotBlank
    String name;

    @NotBlank
    String description;
}
