package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.services.helpers.SpamChecker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpamCheckerTest {

    @Autowired
    SpamChecker spamChecker;

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