package com.xebia.fs101.writerpad.services.domain;

import com.xebia.fs101.writerpad.domain.Comment;
import com.xebia.fs101.writerpad.repository.CommentRepository;
import com.xebia.fs101.writerpad.services.helpers.SpamChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.xebia.fs101.writerpad.utilities.StringUtil.extractId;

@Service
@Transactional
public class CommentService {

    @Autowired
    SpamChecker spamChecker;

    @Autowired
    CommentRepository commentRepository;

    public Optional<Comment> save(Comment toSave) throws IOException {
        toSave.setUpdatedAt();
        if (spamChecker.isSpam(toSave.getBody())) {
            return Optional.empty();
        }
        return Optional.of(this.commentRepository.save(toSave));
    }

    public Optional<List<Comment>> findAllByArticleSlugId(String slugUuid) {
        UUID articleId = extractId(slugUuid);
        return Optional.of(this.commentRepository.findAllByArticleId(articleId));
    }

    public Optional<Comment> find(String commentId) {
        UUID commentUuid = extractId(commentId);
        return Optional.ofNullable(this.commentRepository.findById(commentUuid));
    }

    public void delete(Comment comment) {
        this.commentRepository.deleteById(comment.getId());
    }
}
