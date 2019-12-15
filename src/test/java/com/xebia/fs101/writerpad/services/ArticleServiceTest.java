package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.domain.WriterpadRole;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import com.xebia.fs101.writerpad.repository.UserRepository;
import com.xebia.fs101.writerpad.services.domain.ArticleService;
import com.xebia.fs101.writerpad.services.helpers.image.ImageFinder;
import com.xebia.fs101.writerpad.services.helpers.plagiarism.PlagiarismChecker;
import org.junit.jupiter.api.BeforeEach;
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
    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageFinder imageFinder;
    @Mock
    PlagiarismChecker plagiarismChecker;
    @InjectMocks
    private ArticleService articleService;

    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User.Builder().withUsername("ankursaxena1")
                .withEmail("ankur.saxena1@xebia.com")
                .withPassword("p@ssw0rd")
                .withRole(WriterpadRole.EDITOR)
                .build();
        this.user.setId(UUID.randomUUID());
    }


    @Test
    void should_be_able_delete_an_article() {
        Article article = new Article.Builder()
                .setBody("Aba")
                .setTitle("abc")
                .setDescription("abc")
                .build();
        article.setUser(user);
        String slugUuid = "abc-" + UUID.randomUUID();
        when(articleRepository.findById(extractId(slugUuid))).thenReturn(Optional.of(article));
        doNothing().when(articleRepository).deleteById(extractId(slugUuid));
        // articleService.delete(slugUuid, user);
        articleService.delete(slugUuid);
        verify(articleRepository).findById(extractId(slugUuid));
        verify(articleRepository).deleteById(extractId(slugUuid));
    }

    @Test
    void should_not_be_able_delete_an_article_when_id_is_wrong() {
        Article article = new Article.Builder()
                .setBody("Aba")
                .setTitle("abc")
                .setDescription("abc")
                .build();
        article.setUser(user);
        String slugUuid = "abc-" + UUID.randomUUID();
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));
        // articleService.delete(slugUuid, user);
        articleService.delete(slugUuid);
        verify(articleRepository).findById(any());
        verify(articleRepository).deleteById(any());
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    void verify_findbyid() {

        when(articleRepository.findById(any())).thenReturn(Optional.of(new Article()));
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
        article.setUser(user);
        when(imageFinder.findRandomImage()).thenReturn("");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(plagiarismChecker.isArticlePlagiarised(any())).thenReturn(false);
        articleService.save(article, user);
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
