package com.openclassroom.mdd.mddapi.dtos;

import com.openclassroom.mdd.mddauth.dtos.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class ArticleDto {

    @NotNull
    Long id;

    @NotBlank
    String title;

    @NotBlank
    String content;

    @NotNull
    UserDto author;

    @NotNull
    TopicDto topic;

    @NotNull
    LocalDateTime createdAt;

    @NotNull
    LocalDateTime updatedAt;
}
