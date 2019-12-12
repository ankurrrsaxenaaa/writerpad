package com.xebia.fs101.writerpad.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xebia.fs101.writerpad.utilities.ArticleStatus;
import com.xebia.fs101.writerpad.utilities.StringUtil;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "articles")
public class Article {

    public Article() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    @Column(length = 1500)
    private String body;
    @ElementCollection
    private List<String> tags;
    private String slug;
    private String image;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private boolean favorited = false;
    private int favoritesCount = 0;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status = ArticleStatus.DRAFT;
    @JsonManagedReference
    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "article")
    @JsonBackReference
    private List<Comment> comments;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    private Article(Builder builder) {
        setTitle(builder.title);
        setDescription(builder.description);
        setBody(builder.body);
        setTags(builder.tags);
        setSlug(builder.slug);
        setFavorited(builder.favorited);
        setFavoritesCount(builder.favoritesCount);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imageUrl) {
        this.image = imageUrl;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public UUID getId() {

        return id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getBody() {

        return body;
    }

    public void setBody(String body) {

        this.body = body;
    }

    public List<String> getTags() {

        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    @Override
    public String toString() {
        return "Article{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", body='" + body + '\''
                + ", tags=" + tags
                + ", slug='" + slug + '\''
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", favorited=" + favorited
                + ", favoritesCount=" + favoritesCount
                + ", status=" + status
                + ", user=" + user
                + ", comments=" + comments
                + '}';
    }

    public static final class Builder {
        private String title;
        private String description;
        private String body;
        private List<String> tags;
        private String slug;
        private boolean favorited;
        private int favoritesCount;

        public Builder() {
        }

        public Builder setTitle(String val) {
            title = val;
            return this;
        }

        public Builder setDescription(String val) {
            description = val;
            return this;
        }

        public Builder setBody(String val) {
            body = val;
            return this;
        }

        public Builder setTags(List<String> val) {
            tags = val;
            return this;
        }

        public Builder setSlug(String val) {
            slug = val;
            return this;
        }

        public Builder setFavorited(boolean val) {
            favorited = val;
            return this;
        }

        public Builder setFavoritesCount(int val) {
            favoritesCount = val;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }

    public Article update(Article copyFrom) {
        if (copyFrom.getTitle() != null) {
            this.setTitle(copyFrom.getTitle());
            this.setSlug(StringUtil.generateSlug(copyFrom.getTitle()));
        }
        if (copyFrom.getBody() != null) {
            this.setBody(copyFrom.getBody());
        }
        if (copyFrom.getDescription() != null) {
            this.setDescription(copyFrom.getDescription());
        }
        if (copyFrom.getTags() != null) {
            this.setTags(copyFrom.getTags());
        }
        this.setUpdatedAt();
        return this;
    }
}
