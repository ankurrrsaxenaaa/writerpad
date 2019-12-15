package com.xebia.fs101.writerpad.api.representations;

import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.domain.WriterpadRole;

import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private WriterpadRole role;
    private boolean following;
    private int followerCount;
    private int followingCount;

    private UserResponse(Builder builder) {
        setId(builder.id);
        setUsername(builder.username);
        setEmail(builder.email);
        setRole(builder.role);
        setFollowing(builder.following);
        setFollowerCount(builder.followerCount);
        setFollowingCount(builder.followingCount);
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public WriterpadRole getRole() {
        return role;
    }

    public void setRole(WriterpadRole role) {
        this.role = role;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public static final class Builder {
        private UUID id;
        private String username;
        private String email;
        private WriterpadRole role;
        private boolean following;
        private int followerCount;
        private int followingCount;

        public Builder() {
        }

        public Builder withId(UUID val) {
            id = val;
            return this;
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withRole(WriterpadRole val) {
            role = val;
            return this;
        }

        public Builder withFollowing(boolean val) {
            following = val;
            return this;
        }

        public Builder withFollowerCount(int val) {
            followerCount = val;
            return this;
        }

        public Builder withFollowingCount(int val) {
            followingCount = val;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this);
        }
    }

    public static UserResponse from(User user) {
        return new Builder()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withEmail(user.getUsername())
                .withRole(user.getRole())
                .withFollowing(user.isFollowing())
                .withFollowingCount(user.getFollowingCount())
                .withFollowerCount(user.getFollowerCount())
                .build();
    }
}
