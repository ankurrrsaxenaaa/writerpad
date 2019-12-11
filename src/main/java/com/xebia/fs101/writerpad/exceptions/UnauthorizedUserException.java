package com.xebia.fs101.writerpad.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(value = UNAUTHORIZED, reason = "The user is unauthorized to perform this action.")
public class UnauthorizedUserException extends RuntimeException {
}
