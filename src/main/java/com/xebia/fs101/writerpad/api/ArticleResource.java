package com.xebia.fs101.writerpad.api;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.request_model.ArticleRequest;
import com.xebia.fs101.writerpad.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/articles")
public class ArticleResource {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> saveArticle(@RequestBody ArticleRequest article) {
        boolean articleValidity = false;
        try {
            articleValidity = articleService.isValidArticle(article);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        if (articleValidity) {
            Article savedArticle = articleService.saveArticle(article);
            return new ResponseEntity<>(savedArticle, CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
