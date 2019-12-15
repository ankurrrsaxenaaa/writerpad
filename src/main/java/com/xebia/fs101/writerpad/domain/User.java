package com.xebia.fs101.writerpad.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Article> articles;
    @Enumerated(value = EnumType.STRING)
    private WriterpadRole role;
    private boolean following;
    private int followerCount;
    private int followingCount;

    public User() {
    }

    public User(User other) {
        this.id = other.id;
        this.username = other.username;
        this.email = other.email;
        this.password = other.password;
        this.articles = other.articles;
        this.role = other.role;
        this.following = other.following;
        this.followerCount = other.followerCount;
        this.followingCount = other.followingCount;
    }

    private User(Builder builder) {
        setUsername(builder.username);
        setEmail(builder.email);
        setPassword(builder.password);
        setArticles(builder.articles);
        setRole(builder.role);
        following = false;
        followerCount = 0;
        followingCount = 0;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public WriterpadRole getRole() {
        return role;
    }

    public void setRole(WriterpadRole role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<Article> getArticles() {
        return articles;
    }

    public boolean getFollowing() {
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

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", role='" + role + '\''
                + '}';
    }


    public static final class Builder {
        private String username;
        private String email;
        private String password;
        private List<Article> articles;
        private WriterpadRole role;
        private boolean following;
        private int followerCount;
        private int followingCount;

        public Builder() {
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withPassword(String val) {
            password = val;
            return this;
        }

        public Builder withArticles(List<Article> val) {
            articles = val;
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

        public User build() {
            return new User(this);
        }
    }
}

