package com.openclassroom.mdd.mddapi.controllers;

import com.openclassroom.mdd.mddapi.dtos.ArticleCreateReq;
import com.openclassroom.mdd.mddapi.entities.Article;
import com.openclassroom.mdd.mddapi.mappers.ArticleMapper;
import com.openclassroom.mdd.mddapi.services.ArticleService;
import com.openclassroom.mdd.mddapi.services.SubsciptionService;
import com.openclassroom.mdd.mddauth.entities.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class SubscriptionController {

    private final SubsciptionService subscriptionService;

    @PostMapping("/api/subscription/{topicId}")
    public ResponseEntity<Boolean> subscribeToTopic(
        @PathVariable Long topicId
    ) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        subscriptionService.subscribeToTopic(topicId, user);

        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/api/subscription/{topicId}")
    public ResponseEntity<Boolean> unsubscribeFromTopic(
        @PathVariable Long topicId
    ) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        subscriptionService.unsubscribleFromTopic(topicId, user);

        return ResponseEntity.ok(true);
    }
}
