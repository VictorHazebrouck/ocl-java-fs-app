package com.openclassroom.mdd.mddapi.services;

import com.openclassroom.mdd.mddapi.dtos.TopicAmISubscribedDto;
import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.repositories.SubscriptionRepository;
import com.openclassroom.mdd.mddapi.repositories.TopicRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final SubscriptionRepository subsciptionRepository;

    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    public List<TopicAmISubscribedDto> getTopicsWithSubscription(User user) {
        List<Topic> topics = topicRepository.findAll();

        return topics
            .stream()
            .map(topic ->
                new TopicAmISubscribedDto(
                    topic.getId(),
                    topic.getName(),
                    topic.getDescription(),
                    subsciptionRepository.existsByTopicAndUser(topic, user),
                    topic.getCreatedAt(),
                    topic.getUpdatedAt()
                )
            )
            .toList();
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
