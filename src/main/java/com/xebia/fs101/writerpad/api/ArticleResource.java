package com.xebia.fs101.writerpad.api;

import com.xebia.fs101.writerpad.api.representations.ArticleRequest;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.services.ArticleService;
import com.xebia.fs101.writerpad.utilities.ArticleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleResource {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> create(@Valid
                                          @RequestBody ArticleRequest articleRequest) {
        Article toSave = articleRequest.toArticle();
        Article saved = articleService.save(toSave);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @PatchMapping("/{slugUuid}")
    public ResponseEntity<Article> update(@RequestBody ArticleRequest articleRequest,
                                          @PathVariable("slugUuid") String slugUuid) {
        System.out.println(articleRequest.toString());
        Article copyFrom = articleRequest.toArticle();
        Optional<Article> updatedArticle = articleService.update(slugUuid, copyFrom);
        return updatedArticle.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{slugUuid}")
    public ResponseEntity<Article> getById(@PathVariable("slugUuid") String slugUuid) {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (article.isPresent()) {
            Article found = article.get();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(found);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Article>> getByStatus(
            @RequestParam("status") String status,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Article> pageResult = articleService.findByStatus(status, pageable);
        List<Article> found = pageResult.getContent();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(found);
    }

    @GetMapping
    public ResponseEntity<List<Article>> listAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Article> pageResult = articleService.findAll(pageable);
        List<Article> found = pageResult.getContent();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(found);
    }

    @DeleteMapping("/{slugUuid}")
    public ResponseEntity<Void> delete(@PathVariable("slugUuid") String slugUuid) {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (article.isPresent()) {
            articleService.delete(article.get());

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @PostMapping("/{slugUuid}/PUBLISH")
    public ResponseEntity<Void> publish(@PathVariable("slugUuid") String slugUuid) {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (article.isPresent() && article.get().getStatus() == ArticleStatus.DRAFT) {
            Article published = articleService.publish(article.get());
            boolean isSent = articleService.sendEmail(published);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } else if (article.isPresent() && article.get().getStatus() == ArticleStatus.PUBLISHED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

}
