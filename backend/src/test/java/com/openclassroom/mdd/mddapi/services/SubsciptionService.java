package com.openclassroom.mdd.mddapi.services;

import com.openclassroom.mdd.mddapi.entities.Subscription;
import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.repositories.SubscriptionRepository;
import com.openclassroom.mdd.mddapi.repositories.TopicRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SubsciptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final TopicRepository topicRepository;

    public void subscribeToTopic(Long topicId, User user) {
        Topic topic = topicRepository.getReferenceById(topicId);
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
    }

    public void unsubscribleFromTopic(Long topicId, User user) {
        Topic topic = topicRepository.getReferenceById(topicId);
        Subscription subscription = subscriptionRepository
            .getByTopicAndUser(topic, user)
            .orElseThrow(() -> new RuntimeException());
        subscriptionRepository.delete(subscription);
    }

    public List<Subscription> getSubscriptions(User user) {
        return subscriptionRepository.getByUser(user);
    }

    public boolean existsByTopicAndUser(Topic topic, User user) {
        return subscriptionRepository.existsByTopicAndUser(topic, user);
    }
}
