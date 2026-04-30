package com.openclassroom.mdd.mddapi.mappers;

import com.openclassroom.mdd.mddapi.dtos.ArticleDto;
import com.openclassroom.mdd.mddapi.entities.Article;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDto toDto(Article article);
    List<ArticleDto> toDto(List<Article> articles);
}
