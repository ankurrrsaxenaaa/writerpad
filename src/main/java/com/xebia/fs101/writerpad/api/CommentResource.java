package com.xebia.fs101.writerpad.api;

import com.xebia.fs101.writerpad.api.representations.CommentRequest;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.domain.Comment;
import com.xebia.fs101.writerpad.services.ArticleService;
import com.xebia.fs101.writerpad.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class CommentResource {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;


    @PostMapping("/{slugUuid}/comments")
    public ResponseEntity<Comment> post(@Valid @RequestBody CommentRequest commentRequest,
                                        @PathVariable("slugUuid") String slugUuid,
                                        HttpServletRequest request) throws IOException {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (article.isPresent()) {
            Article found = article.get();
            Comment toSave = commentRequest.toComment(found, request.getRemoteAddr());
            Optional<Comment> saved = commentService.save(toSave);
            return saved.map(comment -> ResponseEntity.status(HttpStatus.CREATED)
                    .body(comment)).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @GetMapping("/{slugUuid}/comments")
    public ResponseEntity<List<Comment>> list(@PathVariable("slugUuid") String slugUuid) {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (article.isPresent()) {
            Optional<List<Comment>> comments = commentService.findAllByArticleSlugId(slugUuid);
            if (comments.isPresent()) {
                List<Comment> found = comments.get();
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(found);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @DeleteMapping("/{slugUuid}/comments/{id}")
    public ResponseEntity<Void> delete(@PathVariable("slugUuid") String slugUuid,
                                       @PathVariable("id") String commentId) {
        Optional<Article> article = articleService.findBySlugId(slugUuid);
        if (article.isPresent()) {
            Optional<Comment> comment = commentService.find(commentId);
            if (comment.isPresent()) {
                commentService.delete(comment.get());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }
}
