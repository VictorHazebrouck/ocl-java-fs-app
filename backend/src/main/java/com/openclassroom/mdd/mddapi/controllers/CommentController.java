package com.openclassroom.mdd.mddapi.controllers;

import com.openclassroom.mdd.mddapi.dtos.CommentCreateReq;
import com.openclassroom.mdd.mddapi.dtos.CommentDto;
import com.openclassroom.mdd.mddapi.entities.Comment;
import com.openclassroom.mdd.mddapi.mappers.CommentMapper;
import com.openclassroom.mdd.mddapi.services.CommentService;
import com.openclassroom.mdd.mddauth.entities.User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<CommentDto> createComment(
        @Valid @RequestBody CommentCreateReq req
    ) {
        User author = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        Comment comment = commentService.createComment(
            req.getContent(),
            req.getArticleId(),
            author
        );

        return ResponseEntity.ok(CommentMapper.INSTANCE.toDto(comment));
    }

    @GetMapping("/api/comment/{articleId}")
    public ResponseEntity<List<CommentDto>> getComments(
        @PathVariable Long articleId
    ) {
        List<Comment> comments = commentService.getCommentsForArticle(
            articleId
        );

        System.out.println("comments: ");
        System.out.println(comments);
        System.out.println("comments mapped: ");
        System.out.println(CommentMapper.INSTANCE.toDto(comments));

        return ResponseEntity.ok(CommentMapper.INSTANCE.toDto(comments));
    }
}
