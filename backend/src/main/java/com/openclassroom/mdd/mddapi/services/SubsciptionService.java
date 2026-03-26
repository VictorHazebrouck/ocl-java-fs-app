package com.openclassroom.mdd.mddapi.services;

import com.openclassroom.mdd.mddapi.entities.Subscription;
import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.repositories.SubscriptionRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SubsciptionService {

    private final SubscriptionRepository subscriptionRepository;

    public void subscribeToTopic(Topic topic, User user) {
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
    }

    public void unsubscribleFromTopic(Topic topic, User user) {
        Subscription subscription = subscriptionRepository
            .getByTopicAndUser(topic, user)
            .orElseThrow(() -> new RuntimeException());
        subscriptionRepository.delete(subscription);
    }

    public List<Subscription> getSubscriptions(User user) {
        return subscriptionRepository.getByUser(user);
    }
}
