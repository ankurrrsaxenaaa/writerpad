package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Article;

public interface EmailService {
    void sendEmail(Article article) throws Exception;
}
