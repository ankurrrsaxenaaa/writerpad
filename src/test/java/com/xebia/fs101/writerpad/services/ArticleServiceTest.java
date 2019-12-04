package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static com.xebia.fs101.writerpad.utilities.StringUtil.extractId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    private ArticleRepository articleRepository;
    @InjectMocks
    private ArticleService articleService;

    @Test
    void should_return_an_empty_optional_when_article_id_is_wrong() {
        Article copyFrom = new Article.Builder()
                .setBody("Aba")
                .setTitle("abc")
                .setDescription("abc")
                .build();
        String slugUuid = "abc" + UUID.randomUUID();
        when(articleRepository.findById(extractId(slugUuid))).thenReturn(Optional.empty());
        articleService.update(slugUuid, copyFrom);
        verify(articleRepository).findById(any());
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void should_return_an_optional_article_object() {
        Article copyFrom = new Article.Builder()
                .setBody("Aba")
                .setTitle("abc")
                .setDescription("abc")
                .build();
        String slugUuid = "abc-" + UUID.randomUUID().toString();
        Article article = new Article();
        when(articleRepository.findById(extractId(slugUuid))).thenReturn(Optional.of(article));
        when(articleRepository.save(article)).thenReturn(article);
        articleService.update(slugUuid, copyFrom);
        verify(articleRepository).findById(extractId(slugUuid));
        verify(articleRepository).save(article);
    }

    @Test
    void should_be_able_delete_an_article() {
        String slugUuid = "abc-" + UUID.randomUUID();
        when(articleRepository.findById(extractId(slugUuid))).thenReturn(Optional.of(new Article()));
        doNothing().when(articleRepository).deleteById(extractId(slugUuid));
        articleService.findBySlugId(slugUuid);
        articleService.delete(extractId(slugUuid));
        verify(articleRepository).findById(extractId(slugUuid));
        verify(articleRepository).deleteById(extractId(slugUuid));
    }

    @Test
    void should_not_be_able_delete_an_article_when_id_is_wrong() {
        String slugUuid = "abc-" + UUID.randomUUID();
        articleService.delete(extractId(slugUuid));
        verify(articleRepository).deleteById((extractId(slugUuid)));
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void verify_findbyid() {
        articleService.findBySlugId(UUID.randomUUID().toString());
        verify(articleRepository).findById(any());
    }

    @Test
    void verify_save() {
        Article article = new Article.Builder()
                .setTitle("Title")
                .setBody("Body")
                .setDescription("Description")
                .build();
        articleService.save(article);
        verify(articleRepository).save(any());
    }

    @Test
    void verify_findAll() {
        Pageable pageable = PageRequest.of(0, 1);
        articleService.findAll(pageable);
        verify(articleRepository).findAll(pageable);
    }

    @Test
    void should_be_able_to_publish_the_article() {
        Article article = new Article.Builder()
                .setBody("Aba")
                .setTitle("abc")
                .setDescription("abc")
                .build();
        Article t = new Article();
        when(articleRepository.save(article)).thenReturn(t);
        articleService.publish(article);
        verify(articleRepository).save(article);
    }
}
