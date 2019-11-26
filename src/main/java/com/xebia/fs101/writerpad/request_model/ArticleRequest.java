package com.xebia.fs101.writerpad.request_model;

import java.util.Arrays;

public class ArticleRequest {


    private String title;
    private String description;
    private String body;
    private String[] tags;
    private String featuredImage;

    public ArticleRequest(String title, String description, String body, String[] tags, String featuredImage) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
        this.featuredImage = featuredImage;
    }

    private ArticleRequest(Builder builder) {
        title = builder.title;
        description = builder.description;
        body = builder.body;
        tags = builder.tags;
        featuredImage = builder.featuredImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }

    public String[] getTags() {
        return tags;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    @Override
    public String toString() {
        return "ArticleRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", featuredImage='" + featuredImage + '\'' +
                '}';
    }

    public static final class Builder {
        private String title;
        private String description;
        private String body;
        private String[] tags;
        private String featuredImage;

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

        public Builder setTags(String[] val) {
            tags = val;
            return this;
        }

        public Builder setFeaturedImage(String val) {
            featuredImage = val;
            return this;
        }

        public ArticleRequest build() {
            return new ArticleRequest(this);
        }
    }
}
