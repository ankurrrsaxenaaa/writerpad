package com.xebia.fs101.writerpad.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private Article article;
    private String body;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private String ipAddress;

    private Comment(Builder builder) {
        setArticle(builder.article);
        setBody(builder.body);
        setIpAddress(builder.ipAddress);
    }

    public Comment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


    public static final class Builder {
        private Article article;
        private String body;
        private String ipAddress;

        public Builder() {
        }

        public Builder setArticle(Article val) {
            article = val;
            return this;
        }

        public Builder setBody(String val) {
            body = val;
            return this;
        }

        public Builder setIpAddress(String val) {
            ipAddress = val;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "article=" + article +
                    ", body='" + body + '\'' +
                    ", ipAddress='" + ipAddress + '\'' +
                    '}';
        }
    }
}

