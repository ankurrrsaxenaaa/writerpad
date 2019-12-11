package com.xebia.fs101.writerpad.api;

import com.xebia.fs101.writerpad.api.representations.ArticleRequest;
import com.xebia.fs101.writerpad.api.representations.ArticleResponse;
import com.xebia.fs101.writerpad.api.representations.TagResponse;
import com.xebia.fs101.writerpad.api.representations.TimeToRead;
import com.xebia.fs101.writerpad.api.representations.TimeToReadResponse;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.services.domain.ArticleService;
import com.xebia.fs101.writerpad.services.helpers.EmailService;
import com.xebia.fs101.writerpad.services.helpers.TimeService;
import com.xebia.fs101.writerpad.services.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/articles")
public class ArticleResource {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    TimeService timeService;

    @PostMapping
    public ResponseEntity<ArticleResponse> create(
            @CurrentUser User user,
            @Valid @RequestBody ArticleRequest articleRequest) {
        Article toSave = articleRequest.toArticle();
        Article saved = articleService.save(toSave, user);
        return ResponseEntity
                .status(CREATED)
                .body(ArticleResponse.from(saved));
    }

    @PatchMapping("/{slugUuid}")
    public ResponseEntity<ArticleResponse> update(
            @CurrentUser User user,
            @RequestBody ArticleRequest articleRequest,
            @PathVariable("slugUuid") String slugUuid) {
        Article copyFrom = articleRequest.toArticle();
        Article updatedArticle = articleService.update(slugUuid, copyFrom, user);
        return ResponseEntity
                .status(OK)
                .body(ArticleResponse.from(updatedArticle));
    }

    @GetMapping("/{slugUuid}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable("slugUuid") String slugUuid) {
        Article article = articleService.findBySlugId(slugUuid);
        return ResponseEntity
                .status(CREATED)
                .body(ArticleResponse.from(article));
    }

    @GetMapping("/")
    public ResponseEntity<List<ArticleResponse>> getByStatus(
            @RequestParam("status") String status,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Article> pageResult = articleService.findByStatus(status, pageable);
        List<Article> found = pageResult.getContent();
        List<ArticleResponse> articleResponse = found.stream()
                .map(ArticleResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity
                .status(OK)
                .body(articleResponse);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> listAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Article> pageResult = articleService.findAll(pageable);
        List<Article> found = pageResult.getContent();
        List<ArticleResponse> articleResponse = found.stream()
                .map(ArticleResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity
                .status(OK)
                .body(articleResponse);
    }

    @DeleteMapping("/{slugUuid}")
    public ResponseEntity<Void> delete(
            @CurrentUser User user,
            @PathVariable("slugUuid") String slugUuid) {
        articleService.delete(slugUuid, user);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @PostMapping("/{slugUuid}/PUBLISH")
    public ResponseEntity<Void> publish(
            @PathVariable("slugUuid") String slugUuid) throws Exception {
        Article article = articleService.findBySlugId(slugUuid);
        if (articleService.isDraft(article.getStatus())) {
            Article published = articleService.publish(article);
            emailService.sendEmail(published);
            return ResponseEntity.status(NO_CONTENT)
                    .build();
        } else if (!articleService.isDraft(article.getStatus())) {
            return ResponseEntity.status(BAD_REQUEST)
                    .build();
        }
        return ResponseEntity.status(NOT_FOUND)
                .build();
    }

    @GetMapping("/{slugUuid}/timetoread")
    public ResponseEntity<TimeToReadResponse> timeToRead(
            @PathVariable("slugUuid") String slugUuid) {
        Article article = articleService.findBySlugId(slugUuid);
        int readingTimeInSeconds = timeService.readingTimeInSeconds(article.getBody());
        TimeToRead timeToRead = new TimeToRead();
        timeToRead.setReadingTime(readingTimeInSeconds % 60, readingTimeInSeconds / 60);
        return ResponseEntity.status(OK)
                .body(new TimeToReadResponse(slugUuid, timeToRead));
    }

    @GetMapping("/tags")
    public ResponseEntity<List<TagResponse>> getTags() {
        Map<String, Long> tags = articleService.getTags();
        List<TagResponse> tagResponse = tags.entrySet()
                .stream()
                .map(t -> new TagResponse(t.getKey(), t.getValue()))
                .collect(Collectors.toList());
        return ResponseEntity.status(OK)
                .body(tagResponse);
    }

    @PutMapping("/{slugUuid}/FAVOURITE")
    public ResponseEntity<Void> favouriteArticle(
            @PathVariable String slugUuid) {
        Article article = articleService.findBySlugId(slugUuid);
        articleService.favourite(article);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping("/{slugUuid}/UNFAVOURITE")
    public ResponseEntity<Void> unfavouriteArticle(
            @PathVariable("slugUuid") String slugUuid) {
        Article article = articleService.findBySlugId(slugUuid);
        articleService.unfavourite(article);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
