package com.xebia.fs101.writerpad.api;

import com.xebia.fs101.writerpad.api.representations.ProfileResponse;
import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.services.domain.ProfileService;
import com.xebia.fs101.writerpad.services.domain.UserService;
import com.xebia.fs101.writerpad.services.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/profiles")
public class ProfileResource {

    @Autowired
    UserService userService;
    @Autowired
    ProfileService profileService;

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> get(@CurrentUser User user,
                                               @PathVariable String username) {
        User found = userService.find(username);
        if (user == null) {
            return ResponseEntity.status(OK).body(ProfileResponse.from(found));
        }
        User searchingUser = userService.find(user.getUsername());
        System.out.println(searchingUser);
        return ResponseEntity.status(OK).body(ProfileResponse.from(searchingUser, found));
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> follow(@CurrentUser User user,
                                                  @PathVariable String username) {
        User userToFollow = userService.find(username);
        User followingUser = profileService.follow(user, userToFollow);
        return ResponseEntity.status(OK).body(ProfileResponse.from(followingUser, userToFollow));
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> unfollow(@CurrentUser User user,
                                                    @PathVariable String username) {
        User usertoUnfollow = userService.find(username);
        User unfollowingUser = profileService.unfollow(user, usertoUnfollow);
        return ResponseEntity.status(OK).body(ProfileResponse.from(unfollowingUser,
                usertoUnfollow));
    }
}
