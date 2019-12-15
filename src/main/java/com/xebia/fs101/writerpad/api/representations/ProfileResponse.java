package com.xebia.fs101.writerpad.api.representations;

import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.domain.WriterpadRole;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProfileResponse {
    private String username;
    private boolean following;
    private int followerCount;
    private int followingCount;
    private List<ArticleResponse> articles;

    public ProfileResponse() {
    }

    private ProfileResponse(Builder builder) {
        setUsername(builder.username);
        setFollowing(builder.following);
        setFollowerCount(builder.followerCount);
        setFollowingCount(builder.followingCount);
        setArticles(builder.articles);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<ArticleResponse> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleResponse> articles) {
        this.articles = articles;
    }


    public static ProfileResponse from(User user) {
        return new ProfileResponse.Builder()
                .withUsername(user.getUsername())
                .withFollowing(user.isFollowing())
                .withFollowingCount(user.getFollowingCount())
                .withFollowerCount(user.getFollowerCount())
                .withArticles(user.getArticles()
                        .stream()
                        .map(article ->
                                new ArticleResponse(article.getId(), article.getTitle()))
                        .collect(Collectors.toList())).build();
    }


    private static class ArticleResponse {

        private UUID id;
        private String title;

        ArticleResponse(UUID id, String title) {
            this.id = id;
            this.title = title;
        }

        public UUID getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }

    public static final class Builder {
        private UUID id;
        private String username;
        private String email;
        private WriterpadRole role;
        private boolean following;
        private int followerCount;
        private int followingCount;
        private List<ArticleResponse> articles;

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

        public Builder withArticles(List<ArticleResponse> val) {
            articles = val;
            return this;
        }

        public ProfileResponse build() {
            return new ProfileResponse(this);
        }
    }
}
