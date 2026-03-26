package com.openclassroom.mdd.mddapi.controllers;

import com.openclassroom.mdd.mddapi.dtos.ArticleCreateReq;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ArticleController {

    @PostMapping("/article")
    public ResponseEntity<ArticleCreateReq> createArticle(
        @Valid @RequestBody ArticleCreateReq req
    ) {
        System.out.println("Hello from createArticle route !");
        return ResponseEntity.ok(req);
    }
}
