package com.openclassroom.mdd.mddapi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddapi.entities.Article;
import com.openclassroom.mdd.mddapi.entities.Comment;
import com.openclassroom.mdd.mddapi.repositories.ArticleRepository;
import com.openclassroom.mdd.mddapi.repositories.CommentRepository;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void should_get_comments_for_article() {
        Long articleId = 1L;

        Article article = new Article();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();

        when(articleRepository.getReferenceById(articleId)).thenReturn(article);
        when(commentRepository.getByArticle(article)).thenReturn(
            List.of(comment1, comment2)
        );

        List<Comment> result = commentService.getCommentsForArticle(articleId);

        assertEquals(2, result.size());
        verify(articleRepository).getReferenceById(articleId);
        verify(commentRepository).getByArticle(article);
    }

    @Test
    void should_create_comment() {
        Long articleId = 1L;

        Article article = new Article();
        User user = new User();

        when(articleRepository.getReferenceById(articleId)).thenReturn(article);
        when(commentRepository.save(any(Comment.class))).thenAnswer(
            invocation -> invocation.getArgument(0)
        );

        Comment result = commentService.createComment(
            "hello world",
            articleId,
            user
        );

        assertNotNull(result);
        assertEquals("hello world", result.getContent());
        assertEquals(article, result.getArticle());
        assertEquals(user, result.getAuthor());

        verify(articleRepository).getReferenceById(articleId);
        verify(commentRepository).save(any(Comment.class));
    }
}
