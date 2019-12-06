package com.xebia.fs101.writerpad.api.representations;

public class TimeToReadResponse {
    private String articleId;
    private TimeToRead timeToRead;

    public TimeToReadResponse() {
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public TimeToRead getTimeToRead() {
        return timeToRead;
    }

    public void setTimeToRead(TimeToRead timeToRead) {
        this.timeToRead = timeToRead;
    }

    public TimeToReadResponse(String articleId, TimeToRead timeToRead) {
        this.articleId = articleId;
        this.timeToRead = timeToRead;
    }
}
