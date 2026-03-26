package com.openclassroom.mdd.mddapi.services;

import com.openclassroom.mdd.mddapi.entities.Article;
import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.repositories.ArticleRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final SubsciptionService subsciptionService;

    public Article createArticle(
        String title,
        String content,
        Topic topic,
        User user
    ) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setTopic(topic);
        article.setAuthor(user);
        return articleRepository.save(article);
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        // @todo throw 404 error
        return articleRepository.findById(id).get();
    }

    public List<Article> getArticlesSubsciptions(User user) {
        List<Topic> topics = subsciptionService
            .getSubscriptions(user)
            .stream()
            .map(sub -> sub.getTopic())
            .toList();

        return articleRepository.getByTopicIn(topics);
    }
}
