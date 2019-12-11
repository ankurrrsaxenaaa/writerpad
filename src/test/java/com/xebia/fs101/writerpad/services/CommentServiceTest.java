package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Comment;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import com.xebia.fs101.writerpad.repository.CommentRepository;
import com.xebia.fs101.writerpad.services.domain.CommentService;
import com.xebia.fs101.writerpad.services.helpers.SpamChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private SpamChecker spamChecker;

    @InjectMocks
    private CommentService commentService;

    @Test
    void should_be_able_to_save_a_comment() throws IOException {
        Comment comment = new Comment.Builder()
                .setBody("comment")
                .build();
        when(commentRepository.save(comment)).thenReturn(comment);
        commentService.save(comment);
        verify(commentRepository).save(comment);
        verifyNoMoreInteractions(commentRepository);
    }

    @Test
    void should_be_able_to_find_all_comments_by_article_id() {
        UUID id = UUID.randomUUID();
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Comment comment3 = new Comment();
        List<Comment> comments = Arrays.asList(comment1, comment2, comment3);
        when(commentRepository.findAllByArticleId(id)).thenReturn(comments);
        List<Comment> saved = commentService.findAllByArticleSlugId( id.toString()).get();
        assertThat(saved).hasSize(3);
        verify(commentRepository).findAllByArticleId(id);
        verifyNoMoreInteractions(commentRepository);
    }
    @Test
    void should_be_able_to_delete_a_comment() {
        Comment comment = new Comment();
        commentService.delete(comment);
        verify(commentRepository).deleteById(comment.getId());
        verifyNoMoreInteractions(commentRepository);
    }
}
