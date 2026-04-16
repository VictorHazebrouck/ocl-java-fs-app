package com.openclassroom.mdd.mddapi.repositories;

import com.openclassroom.mdd.mddapi.entities.Subscription;
import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository
    extends JpaRepository<Subscription, Long>
{
    Optional<Subscription> getByTopicAndUser(Topic topic, User user);
    boolean existsByTopicAndUser(Topic topic, User user);

    List<Subscription> getByUser(User user);
}
