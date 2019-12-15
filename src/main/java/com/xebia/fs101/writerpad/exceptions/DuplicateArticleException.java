package com.xebia.fs101.writerpad.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Article body found plagiarised")
public class DuplicateArticleException extends RuntimeException {
}
