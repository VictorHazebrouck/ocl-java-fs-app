package com.openclassroom.mdd.mddapi.mappers;

import com.openclassroom.mdd.mddapi.dtos.TopicDto;
import com.openclassroom.mdd.mddapi.entities.Topic;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TopicMapper {
    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    TopicDto toDto(Topic topic);
    List<TopicDto> toDto(List<Topic> topics);
}
