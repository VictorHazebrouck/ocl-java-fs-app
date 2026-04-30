package com.openclassroom.mdd.mddapi.mappers;

import com.openclassroom.mdd.mddapi.dtos.CommentDto;
import com.openclassroom.mdd.mddapi.entities.Comment;
import com.openclassroom.mdd.mddauth.mappers.UserMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { UserMapper.class, ArticleMapper.class })
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto toDto(Comment comment);
    List<CommentDto> toDto(List<Comment> comments);
}
