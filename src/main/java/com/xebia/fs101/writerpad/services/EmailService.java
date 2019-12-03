package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Article;

public interface EmailService {
 boolean sendEmail(Article article);
}
