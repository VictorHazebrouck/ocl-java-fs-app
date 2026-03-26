package com.openclassroom.mdd.mddapi.mappers;

import com.openclassroom.mdd.mddapi.dtos.CommentDto;
import com.openclassroom.mdd.mddapi.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto toDto(Comment comment);
}
