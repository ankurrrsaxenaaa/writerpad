package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.exceptions.UserToUnfollowException;
import com.xebia.fs101.writerpad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    UserRepository userRepository;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public User follow(User user, User userToFollow) {
        User followingUser = userRepository.findByUsername(user.getUsername()).get();
        followingUser.setFollowingCount(
                followingUser.getFollowingCount() + 1);
        followingUser.setFollowing(true);
        userToFollow.setFollowerCount(
                userToFollow.getFollowerCount() + 1);
        User saved = userRepository.save(followingUser);
        userRepository.save(userToFollow);
        return saved;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public User unfollow(User user, User usertoUnfollow) throws UserUnfollowingException {
        User unfollowingUser = userRepository.findByUsername(user.getUsername()).get();
        if (unfollowingUser.getFollowingCount() == 0) {
            throw new UserUnfollowingException();
        }
        if (usertoUnfollow.getFollowerCount() == 0) {
            throw new UserToUnfollowException();
        }
        unfollowingUser.setFollowingCount(unfollowingUser
                .getFollowingCount() - 1);
        usertoUnfollow.setFollowerCount(usertoUnfollow
                .getFollowerCount() - 1);
        if (unfollowingUser.getFollowingCount() == 0) {
            unfollowingUser.setFollowing(false);
        }
        User saved = userRepository.save(unfollowingUser);
        userRepository.save(usertoUnfollow);
        return saved;
    }
}
