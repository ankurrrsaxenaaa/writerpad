package com.xebia.fs101.writerpad.api;

import com.xebia.fs101.writerpad.api.representations.ArticleRequest;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleResource {

    @Autowired
    private ArticleService articleService;


    @PostMapping
    public ResponseEntity<Article> createArticle(@Valid
                                                 @RequestBody ArticleRequest articleRequest) {
        Article toSave = articleRequest.toArticle();
        Article saved = articleService.saveArticle(toSave);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @PatchMapping("/{slugUuid}")
    public ResponseEntity<Article> updateArticle(@RequestBody ArticleRequest articleRequest,
                                                 @PathVariable("slugUuid") String slugUuid) {
        Article copyFrom = articleRequest.toArticle();
        Optional<Article> updatedArticle = articleService.update(slugUuid, copyFrom);
        return updatedArticle.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{slugUuid}")
    public ResponseEntity<Article> getArticleById(@PathVariable("slugUuid") String slugUuid) {
        Optional<Article> article = articleService.findArticle(slugUuid);
        if (article.isPresent()) {
            Article found = article.get();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(found);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<Article>> listAllArticles(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Article> pageResult = articleService.findAllArticles(pageable);
        List<Article> found = pageResult.getContent();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(found);
    }

    @DeleteMapping("/{slugUuid}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable("slugUuid") String slugUuid) {
        Optional<Article> article = articleService.findArticle(slugUuid);
        if (article.isPresent()) {
            articleService.deleteArticle(article.get());

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }
}
