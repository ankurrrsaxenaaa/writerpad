package com.xebia.fs101.writerpad.api;


import com.xebia.fs101.writerpad.api.representations.UserRequest;
import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.services.domain.UserService;
import com.xebia.fs101.writerpad.services.security.AdminOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @AdminOnly
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest userRequest) {
        User toSave = userRequest.toUser(passwordEncoder);
        User saved = userService.save(toSave);
        return ResponseEntity.status(CREATED).body(saved);
    }

    @GetMapping
    public String abc() {
        return "abc";
    }

}
