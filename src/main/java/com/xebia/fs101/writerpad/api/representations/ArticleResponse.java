package com.xebia.fs101.writerpad.api.representations;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.utilities.StringUtil;
import com.xebia.fs101.writerpad.utilities.TimeUtil;

public class ArticleResponse {
    private String articleId;
    private TimeToRead timeToRead;

    public ArticleResponse() {
    }

    private ArticleResponse(Builder builder) {
        setArticleId(builder.articleId);
        setTimeToRead(builder.timeToRead);
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

    public static final class Builder {

        private String articleId;
        private TimeToRead timeToRead;
        public Builder() {
        }

        public Builder withArticleId(String val) {
            articleId = val;
            return this;
        }

        public Builder withTimeToRead(String body, int wordsPerMinute) {
            int secs = TimeUtil.getSeconds(body, wordsPerMinute);
            int mins = TimeUtil.getMinutes(body, wordsPerMinute);
            timeToRead = new TimeToRead(mins, secs);
            return this;
        }

        public ArticleResponse build() {
            return new ArticleResponse(this);
        }

    }

    public static ArticleResponse timeToReadResponse(Article article, int wordsPerMinute) {
        return  new ArticleResponse.Builder()
                .withArticleId(StringUtil.generateSlugUuid(article))
                .withTimeToRead(article.getBody(), wordsPerMinute)
                .build();
    }
}
