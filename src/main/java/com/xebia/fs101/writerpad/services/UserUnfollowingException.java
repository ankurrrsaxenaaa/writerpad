package com.xebia.fs101.writerpad.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You have no user to unfollow")
public class UserUnfollowingException extends RuntimeException {
}
