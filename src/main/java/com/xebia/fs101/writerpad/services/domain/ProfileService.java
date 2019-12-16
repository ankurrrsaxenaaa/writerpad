package com.xebia.fs101.writerpad.services.domain;

import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.exceptions.UserAlreadyFollowedException;
import com.xebia.fs101.writerpad.exceptions.UserUnfollowingException;
import com.xebia.fs101.writerpad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ProfileService {
    @Autowired
    UserRepository userRepository;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public User follow(User user, User userToFollow) {
        User followingUser = userRepository.findByUsername(user.getUsername()).get();
        HashSet<String> following = followingUser.getFollowing();
        if (following.contains(userToFollow.getUsername())) {
            throw new UserAlreadyFollowedException();
        }
        followingUser.setFollowingCount(
                followingUser.getFollowingCount() + 1);
        following.add(userToFollow.getUsername());
        followingUser.setFollowing(following);
        userToFollow.setFollowerCount(
                userToFollow.getFollowerCount() + 1);
        User saved = userRepository.save(followingUser);
        userRepository.save(userToFollow);
        return saved;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public User unfollow(User user, User usertoUnfollow) throws UserUnfollowingException {
        User unfollowingUser = userRepository.findByUsername(user.getUsername()).get();
        HashSet<String> following = unfollowingUser.getFollowing();
        if (!following.contains(usertoUnfollow.getUsername())) {
            throw new UserUnfollowingException();
        }
        unfollowingUser.setFollowingCount(unfollowingUser
                .getFollowingCount() - 1);
        usertoUnfollow.setFollowerCount(usertoUnfollow
                .getFollowerCount() - 1);
        following.remove(usertoUnfollow.getUsername());
        unfollowingUser.setFollowing(following);
        User saved = userRepository.save(unfollowingUser);
        userRepository.save(usertoUnfollow);
        return saved;
    }
}
