package com.openclassroom.mdd.mddapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ArticleCreateReq {

    @NotBlank
    String title;

    @NotBlank
    String content;

    @NotNull
    Long topicId;
}
