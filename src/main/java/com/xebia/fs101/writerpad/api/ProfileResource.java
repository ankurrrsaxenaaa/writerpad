package com.xebia.fs101.writerpad.api;

import com.xebia.fs101.writerpad.api.representations.ProfileResponse;
import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.services.domain.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/profiles")
public class ProfileResource {

    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> createUser(@PathVariable String username) {
        User user = userService.find(username);
        return ResponseEntity.status(OK).body(ProfileResponse.from(user));
    }
}
