package com.xebia.fs101.writerpad.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.ElementCollection;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    @Column(length = 1500)
    private String body;
    @ElementCollection
    private List<String> tags;
    private String featuredImage;
    private String slug;
    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private boolean favorited;
    private int favoritesCount;

    private Article(Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setDescription(builder.description);
        setBody(builder.body);
        setTags(builder.tags);
        setFeaturedImage(builder.featuredImage);
        setSlug(builder.slug);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
        setFavorited(builder.favorited);
        setFavoritesCount(builder.favoritesCount);
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
                + ", body='"
                + body + '\''
                + ", tags=" + (tags).toString()
                + ", featuredImage='" + featuredImage + '\''
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", favorited=" + favorited
                + ", favoritesCount=" + favoritesCount
                + '}';
    }

    public static final class Builder {
        private UUID id;
        private String title;
        private String description;
        private String body;
        private List<String> tags;
        private String featuredImage;
        private String slug;
        private Date createdAt;
        private Date updatedAt;
        private boolean favorited;
        private int favoritesCount;

        public Builder() {
        }

        public Builder setId(UUID val) {
            id = val;
            return this;
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

        public Builder setFeaturedImage(String val) {
            featuredImage = val;
            return this;
        }

        public Builder setSlug(String val) {
            slug = val;
            return this;
        }

        public Builder setCreatedAt(Date val) {
            createdAt = val;
            return this;
        }

        public Builder setUpdatedAt(Date val) {
            updatedAt = val;
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
}
