package com.xebia.fs101.writerpad.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User to unfollow has no existing followers")
public class UserToUnfollowException extends RuntimeException {
}
