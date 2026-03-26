package com.openclassroom.mdd.mddapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CommentCreateReq {

    @NotBlank
    String content;

    @NotNull
    Long articleId;
}
