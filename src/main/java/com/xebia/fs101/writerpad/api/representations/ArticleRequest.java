package com.xebia.fs101.writerpad.api.representations;

import com.xebia.fs101.writerpad.domain.Article;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;

import static com.xebia.fs101.writerpad.utilities.StringUtil.generateSlugArray;

public class ArticleRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String body;
    private String[] tags;

    @Override
    public String toString() {
        return "ArticleRequest{"
                + "title='" + title + '\''
                + ", description='" + description + '\''
                + ", body='" + body + '\''
                + ", tags=" + Arrays.toString(tags)
                + '}';
    }

    public ArticleRequest(String title, String description,
                          String body, String[] tags) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
    }

    private ArticleRequest(Builder builder) {
        title = builder.title;
        description = builder.description;
        body = builder.body;
        tags = builder.tags;
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


    public static final class Builder {
        private String title;
        private String description;
        private String body;
        private String[] tags;

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
            tags = val == null ? new String[0] : val;
            return this;
        }

        public ArticleRequest build() {
            return new ArticleRequest(this);
        }
    }

    public Article toArticle() {
        Article article = new Article.Builder()
                .setTitle(this.title)
                .setDescription(this.description)
                .setBody(this.body)
                .build();
        if (this.tags != null) {
            article.setTags(generateSlugArray(this.tags));
        }
        return article;
    }
}
