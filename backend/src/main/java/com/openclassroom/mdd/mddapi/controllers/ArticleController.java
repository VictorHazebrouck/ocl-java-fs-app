package com.openclassroom.mdd.mddapi.controllers;

import com.openclassroom.mdd.mddapi.dtos.ArticleCreateReq;
import com.openclassroom.mdd.mddapi.dtos.ArticleDto;
import com.openclassroom.mdd.mddapi.entities.Article;
import com.openclassroom.mdd.mddapi.mappers.ArticleMapper;
import com.openclassroom.mdd.mddapi.services.ArticleService;
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
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/article")
    public ResponseEntity<ArticleDto> createArticle(
        @Valid @RequestBody ArticleCreateReq req
    ) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        Article newArticle = articleService.createArticle(
            req.getTitle(),
            req.getContent(),
            req.getTopicId(),
            user
        );

        System.out.println("Hello from createArticle route !");
        System.out.println(user);

        return ResponseEntity.ok(ArticleMapper.INSTANCE.toDto(newArticle));
    }

    @GetMapping("/api/article")
    public ResponseEntity<List<ArticleDto>> getArticles() {
        return ResponseEntity.ok(
            ArticleMapper.INSTANCE.toDto(articleService.getArticles())
        );
    }

    @GetMapping("/api/article/{articleId}")
    public ResponseEntity<ArticleDto> subscribeToTopic(
        @PathVariable Long articleId
    ) {
        Article article = articleService.getArticleById(articleId);

        return ResponseEntity.ok(ArticleMapper.INSTANCE.toDto(article));
    }
}
