package com.xebia.fs101.writerpad.helpers.email;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.services.helpers.email.EmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class NoopEmailService implements EmailService {

    @Override
    public void sendEmail(Article article) {
    }
}
