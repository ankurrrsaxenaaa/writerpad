package com.xebia.fs101.writerpad.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestControllerAdvice
public class ExceptionHandlers {
    @ResponseStatus(NO_CONTENT)
    @ExceptionHandler(MailException.class)
    void mailException(Exception e) {

    }

    @ExceptionHandler(WriterpadMailSendException.class)
    ResponseEntity<?> mailException(WriterpadMailSendException e) {
        return ResponseEntity.status(OK).body(e.getContext());
    }
}
