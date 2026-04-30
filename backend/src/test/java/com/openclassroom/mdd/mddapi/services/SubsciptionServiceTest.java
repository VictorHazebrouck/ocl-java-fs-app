package com.openclassroom.mdd.mddapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddapi.entities.Subscription;
import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.repositories.SubscriptionRepository;
import com.openclassroom.mdd.mddapi.repositories.TopicRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubsciptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private SubsciptionService subsciptionServiceTest;

    @Test
    void should_subscribe_user_to_topic() {
        Long topicId = 1L;

        Topic topic = new Topic();
        User user = new User();

        when(topicRepository.getReferenceById(topicId)).thenReturn(topic);

        subsciptionServiceTest.subscribeToTopic(topicId, user);

        verify(topicRepository).getReferenceById(topicId);
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void should_unsubscribe_user_from_topic() {
        Long topicId = 1L;

        Topic topic = new Topic();
        User user = new User();
        Subscription subscription = new Subscription();

        when(topicRepository.getReferenceById(topicId)).thenReturn(topic);
        when(subscriptionRepository.getByTopicAndUser(topic, user)).thenReturn(
            Optional.of(subscription)
        );

        subsciptionServiceTest.unsubscribleFromTopic(topicId, user);

        verify(subscriptionRepository).delete(subscription);
    }

    @Test
    void should_throw_exception_when_unsubscribing_non_existing_subscription() {
        Long topicId = 1L;

        Topic topic = new Topic();
        User user = new User();

        when(topicRepository.getReferenceById(topicId)).thenReturn(topic);
        when(subscriptionRepository.getByTopicAndUser(topic, user)).thenReturn(
            Optional.empty()
        );

        assertThrows(RuntimeException.class, () ->
            subsciptionServiceTest.unsubscribleFromTopic(topicId, user)
        );
    }

    @Test
    void should_return_user_subscriptions() {
        User user = new User();

        List<Subscription> subscriptions = List.of(
            new Subscription(),
            new Subscription()
        );

        when(subscriptionRepository.getByUser(user)).thenReturn(subscriptions);

        List<Subscription> result = subsciptionServiceTest.getSubscriptions(
            user
        );

        assertEquals(2, result.size());
        verify(subscriptionRepository).getByUser(user);
    }

    @Test
    void should_check_existence_of_subscription() {
        Topic topic = new Topic();
        User user = new User();

        when(
            subscriptionRepository.existsByTopicAndUser(topic, user)
        ).thenReturn(true);

        boolean result = subsciptionServiceTest.existsByTopicAndUser(
            topic,
            user
        );

        assertTrue(result);
        verify(subscriptionRepository).existsByTopicAndUser(topic, user);
    }
}
