package com.openclassroom.mdd.mddapi.services;

import com.openclassroom.mdd.mddapi.entities.Article;
import com.openclassroom.mdd.mddapi.entities.Comment;
import com.openclassroom.mdd.mddapi.repositories.ArticleRepository;
import com.openclassroom.mdd.mddapi.repositories.CommentRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<Comment> getCommentsForArticle(Long articleId) {
        Article article = articleRepository.getReferenceById(articleId);
        return commentRepository.getByArticle(article);
    }

    public Comment createComment(String content, Long articleId, User author) {
        Article article = articleRepository.getReferenceById(articleId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setArticle(article);
        comment.setAuthor(author);
        return commentRepository.save(comment);
    }
}
