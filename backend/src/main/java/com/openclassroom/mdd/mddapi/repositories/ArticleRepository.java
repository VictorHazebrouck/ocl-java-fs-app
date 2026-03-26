package com.openclassroom.mdd.mddapi.repositories;

import com.openclassroom.mdd.mddapi.entities.Article;
import com.openclassroom.mdd.mddapi.entities.Topic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> getByTopicIn(List<Topic> topics);
}
