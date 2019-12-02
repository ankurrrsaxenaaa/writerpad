package com.xebia.fs101.writerpad.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SpamCheckerTest {

    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private SpamChecker spamChecker;

    @BeforeEach
    void setUp() throws IOException {
        Mockito.when(resourceLoader.getResource(ArgumentMatchers.anyString()))
                .thenReturn(new ClassPathResource("Spam.txt"));
        spamChecker.init();
    }

    @Test
    void should_return_false_if_spam() throws Exception {
        String commentBody = "Today is a nice day";
        boolean spam = spamChecker.isSpam(commentBody);
        assertThat(spam).isFalse();
    }

    @Test
    void should_return_true_if_spam() throws Exception {
        String commentBody = "Today is a nice day wank";
        boolean spam = spamChecker.isSpam(commentBody);
        assertThat(spam).isTrue();
    }
}