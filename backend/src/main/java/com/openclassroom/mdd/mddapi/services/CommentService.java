package com.openclassroom.mdd.mddapi.services;

import com.openclassroom.mdd.mddapi.entities.Article;
import com.openclassroom.mdd.mddapi.entities.Comment;
import com.openclassroom.mdd.mddapi.repositories.CommentRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getCommentsForArticle(Article article) {
        return commentRepository.getByArticle(article);
    }

    public List<Comment> createComment(
        String content,
        Article article,
        User author
    ) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setArticle(article);
        comment.setAuthor(author);
        return commentRepository.getByArticle(article);
    }
}
