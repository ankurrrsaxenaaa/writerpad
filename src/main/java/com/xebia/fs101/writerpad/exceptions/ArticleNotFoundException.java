package com.xebia.fs101.writerpad.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No article found")
public class ArticleNotFoundException extends RuntimeException {
}
