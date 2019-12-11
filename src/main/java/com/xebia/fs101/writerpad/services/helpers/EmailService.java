package com.xebia.fs101.writerpad.services.helpers;

import com.xebia.fs101.writerpad.domain.Article;

public interface EmailService {
    void sendEmail(Article article) throws Exception;
}
