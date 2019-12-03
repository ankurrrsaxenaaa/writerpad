package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Article;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class NoopEmailService implements EmailService {

    @Override
    public boolean sendEmail(Article article) {
        return true;
    }
}
