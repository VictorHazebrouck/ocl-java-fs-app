package com.openclassroom.mdd.mddapi.dtos;

import com.openclassroom.mdd.mddauth.dtos.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.ToString;
import lombok.Value;

@ToString
@Value
public class CommentDto {

    @NotNull
    Long id;

    @NotBlank
    String content;

    @NotNull
    UserDto author;

    @NotNull
    ArticleDto article;

    @NotNull
    LocalDateTime createdAt;

    @NotNull
    LocalDateTime updatedAt;
}
