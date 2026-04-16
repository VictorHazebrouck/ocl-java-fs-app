package com.openclassroom.mdd.mddapi.services;

import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.repositories.TopicRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    public Topic createTopic(String name, String desc) {
        Topic topic = new Topic();
        topic.setName(name);
        topic.setDescription(desc);
        return topicRepository.save(topic);
    }

    public Topic getTopicById(Long id) {
        return topicRepository.getReferenceById(id);
    }
}
