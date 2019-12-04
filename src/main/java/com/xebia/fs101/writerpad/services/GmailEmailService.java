package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class GmailEmailService implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("email.to")
    String emailTo;

    @Value("email.subject")
    String emailSubject;

    Article article;

    @Override
    public void sendEmail(Article copyFrom) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailTo);
        message.setSubject(emailSubject);
        message.setText("Your article " + article.getTitle() + " has been published!");
        javaMailSender.send(message);
        this.article = copyFrom;
    }
}
