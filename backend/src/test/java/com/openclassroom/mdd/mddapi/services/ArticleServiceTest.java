package com.openclassroom.mdd.mddapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddapi.entities.Article;
import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.repositories.ArticleRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SubsciptionService subsciptionService;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private ArticleService articleService;

    private User user;
    private Topic topic;

    @BeforeEach
    void setUp() {
        user = new User();
        topic = new Topic();
    }

    @Test
    void should_create_article() {
        when(topicService.getTopicById(1L)).thenReturn(topic);
        when(articleRepository.save(any(Article.class))).thenAnswer(i ->
            i.getArgument(0)
        );

        Article result = articleService.createArticle(
            "title",
            "content",
            1L,
            user
        );

        assertEquals("title", result.getTitle());
        assertEquals("content", result.getContent());
        assertEquals(topic, result.getTopic());
        assertEquals(user, result.getAuthor());

        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void should_return_all_articles() {
        List<Article> articles = List.of(new Article(), new Article());

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.getArticles();

        assertEquals(2, result.size());
        verify(articleRepository).findAll();
    }

    @Test
    void should_return_article_by_id() {
        Article article = new Article();

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        Article result = articleService.getArticleById(1L);

        assertNotNull(result);
        assertEquals(article, result);
    }

    @Test
    void should_return_articles_by_subscriptions() {
        Topic t1 = new Topic();

        var subscription = mock(
            com.openclassroom.mdd.mddapi.entities.Subscription.class
        );
        when(subscription.getTopic()).thenReturn(t1);

        when(subsciptionService.getSubscriptions(user)).thenReturn(
            List.of(subscription)
        );

        Article article = new Article();

        when(articleRepository.getByTopicIn(List.of(t1))).thenReturn(
            List.of(article)
        );

        List<Article> result = articleService.getArticlesSubsciptions(user);

        assertEquals(1, result.size());
        verify(articleRepository).getByTopicIn(List.of(t1));
    }
}
