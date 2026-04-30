package com.openclassroom.mdd.mddapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddapi.dtos.TopicAmISubscribedDto;
import com.openclassroom.mdd.mddapi.entities.Subscription;
import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.repositories.SubscriptionRepository;
import com.openclassroom.mdd.mddapi.repositories.TopicRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private SubscriptionRepository subsciptionRepository;

    @InjectMocks
    private TopicService topicService;

    @Test
    void should_return_all_topics() {
        List<Topic> topics = List.of(new Topic(), new Topic());

        when(topicRepository.findAll()).thenReturn(topics);

        List<Topic> result = topicService.getTopics();

        assertEquals(2, result.size());
        verify(topicRepository).findAll();
    }

    @Test
    void should_create_topic() {
        when(topicRepository.save(any(Topic.class))).thenAnswer(invocation ->
            invocation.getArgument(0)
        );

        Topic result = topicService.createTopic("name", "desc");

        assertEquals("name", result.getName());
        assertEquals("desc", result.getDescription());

        verify(topicRepository).save(any(Topic.class));
    }

    @Test
    void should_get_topic_by_id() {
        Topic topic = new Topic();

        when(topicRepository.getReferenceById(1L)).thenReturn(topic);

        Topic result = topicService.getTopicById(1L);

        assertEquals(topic, result);
        verify(topicRepository).getReferenceById(1L);
    }

    @Test
    void should_return_topics_with_subscription_flag() {
        User user = new User();

        Topic topic1 = new Topic();
        topic1.setId(1L);
        topic1.setName("t1");
        topic1.setDescription("d1");

        Topic topic2 = new Topic();
        topic2.setId(2L);
        topic2.setName("t2");
        topic2.setDescription("d2");

        when(topicRepository.findAll()).thenReturn(List.of(topic1, topic2));

        when(
            subsciptionRepository.existsByTopicAndUser(topic1, user)
        ).thenReturn(true);
        when(
            subsciptionRepository.existsByTopicAndUser(topic2, user)
        ).thenReturn(false);

        List<TopicAmISubscribedDto> result =
            topicService.getTopicsWithSubscription(user);

        assertEquals(2, result.size());

        assertTrue(result.get(0).getAmISubscribed());
        assertFalse(result.get(1).getAmISubscribed());
    }

    @Test
    void should_return_topics_subscribed_by_user() {
        User user = new User();

        Topic topic = new Topic();

        Subscription sub = new Subscription();
        sub.setTopic(topic);

        when(subsciptionRepository.getByUser(user)).thenReturn(List.of(sub));

        List<Topic> result = topicService.getTopicsSubscribed(user);

        assertEquals(1, result.size());
        assertEquals(topic, result.get(0));
    }
}
