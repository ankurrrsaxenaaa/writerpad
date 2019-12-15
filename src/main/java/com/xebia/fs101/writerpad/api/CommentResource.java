package com.xebia.fs101.writerpad.api;

import com.xebia.fs101.writerpad.api.representations.CommentRequest;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.domain.Comment;
import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.services.domain.ArticleService;
import com.xebia.fs101.writerpad.services.domain.CommentService;
import com.xebia.fs101.writerpad.services.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/articles")
public class CommentResource {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;


    @PostMapping("/{slugUuid}/comments")
    public ResponseEntity<Comment> post(@CurrentUser User user,
                                        @Valid @RequestBody CommentRequest commentRequest,
                                        @PathVariable("slugUuid") String slugUuid,
                                        HttpServletRequest request) throws IOException {
        Article article = articleService.findBySlugId(slugUuid);
        Comment toSave = commentRequest.toComment(article, request.getRemoteAddr());
        Optional<Comment> saved = commentService.save(toSave);
        return saved.map(comment -> ResponseEntity.status(CREATED)
                .body(comment)).orElseGet(() -> ResponseEntity.status(BAD_REQUEST)
                .build());
    }

    @GetMapping("/{slugUuid}/comments")
    public ResponseEntity<List<Comment>> list(@PathVariable("slugUuid") String slugUuid) {
        articleService.findBySlugId(slugUuid);
        Optional<List<Comment>> comments = commentService.findAllByArticleSlugId(slugUuid);
        if (!comments.isPresent()) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .build();
        }
        List<Comment> found = comments.get();
        return ResponseEntity
                .status(CREATED)
                .body(found);
    }

    @DeleteMapping("/{slugUuid}/comments/{id}")
    public ResponseEntity<Void> delete(@CurrentUser User user,
                                       @PathVariable("slugUuid") String slugUuid,
                                       @PathVariable("id") String commentId) {
        articleService.findBySlugId(slugUuid);
        Optional<Comment> comment = commentService.find(commentId);
        if (!comment.isPresent()) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .build();
        }
        commentService.delete(comment.get());
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
