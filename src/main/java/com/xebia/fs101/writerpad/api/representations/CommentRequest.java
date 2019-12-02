package com.xebia.fs101.writerpad.api.representations;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.domain.Comment;

import javax.validation.constraints.NotBlank;

public class CommentRequest {
    @NotBlank
    private String body;

    public String getBody() {
        return body;
    }

    public CommentRequest() {
    }

    public void setBody(String body) {
        this.body = body;
    }

    public CommentRequest(String body) {
        this.body = body;
    }

    public Comment toComment(Article article, String remoteAddress) {

        return new Comment.Builder()
                .setArticle(article)
                .setBody(this.body)
                .setIpAddress(remoteAddress)
                .build();

    }
}
