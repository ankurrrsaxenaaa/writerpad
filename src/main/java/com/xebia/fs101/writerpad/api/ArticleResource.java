package com.xebia.fs101.writerpad.api;

import com.xebia.fs101.writerpad.api.representations.ArticleRequest;
import com.xebia.fs101.writerpad.api.representations.TagResponse;
import com.xebia.fs101.writerpad.api.representations.TimeToRead;
import com.xebia.fs101.writerpad.api.representations.TimeToReadResponse;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.services.ArticleService;
import com.xebia.fs101.writerpad.services.EmailService;
import com.xebia.fs101.writerpad.services.TimeService;
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
import java.util.Optional;
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
    public ResponseEntity<Article> create(@Valid
                                          @RequestBody ArticleRequest articleRequest) {
        Article toSave = articleRequest.toArticle();
        Article saved = articleService.save(toSave);
        return ResponseEntity
                .status(CREATED)
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
                    .status(CREATED)
                    .body(found);
        }
        return ResponseEntity.status(NOT_FOUND)
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
                .status(OK)
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
                .status(OK)
                .body(found);
    }

    @DeleteMapping("/{slugUuid}")
    public ResponseEntity<Void> delete(@PathVariable("slugUuid") String slugUuid) {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (!article.isPresent()) {
            return ResponseEntity.status(NOT_FOUND)
                    .build();
        }
        articleService.delete(article.get().getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{slugUuid}/PUBLISH")
    public ResponseEntity<Void> publish(
            @PathVariable("slugUuid") String slugUuid) throws Exception {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (article.isPresent() && articleService.isDraft(article.get().getStatus())) {
            Article published = articleService.publish(article.get());
            emailService.sendEmail(published);
            return ResponseEntity.status(NO_CONTENT)
                    .build();
        } else if (article.isPresent() && !articleService.isDraft(article.get().getStatus())) {
            return ResponseEntity.status(BAD_REQUEST)
                    .build();
        }
        return ResponseEntity.status(NOT_FOUND)
                .build();
    }

    @GetMapping("/{slugUuid}/timetoread")
    public ResponseEntity<TimeToReadResponse> timeToRead(
            @PathVariable("slugUuid") String slugUuid) {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (!article.isPresent()) {
            return ResponseEntity.status(NOT_FOUND)
                    .build();
        }
        Article found = article.get();
        int readingTimeInSeconds = timeService.readingTimeInSeconds(found.getBody());
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
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (!article.isPresent()) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
        Article found = article.get();
        articleService.favourite(found);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping("/{slugUuid}/UNFAVOURITE")
    public ResponseEntity<Void> unfavouriteArticle(
            @PathVariable("slugUuid") String slugUuid) {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (!article.isPresent()) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
        Article found = article.get();
        articleService.unfavourite(found);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
