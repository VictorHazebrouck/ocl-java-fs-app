package com.openclassroom.mdd.mddapi.controllers;

import com.openclassroom.mdd.mddapi.dtos.TopicDto;
import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.mappers.TopicMapper;
import com.openclassroom.mdd.mddapi.services.TopicService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/api/topic")
    public ResponseEntity<List<TopicDto>> getAllTopics() {
        List<Topic> topics = topicService.getTopics();

        return ResponseEntity.ok(TopicMapper.INSTANCE.toDto(topics));
    }
}
