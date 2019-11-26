package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.request_model.ArticleRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ArticleServiceTest {

    @Test
    void should_save_Article_from_ArticleRequest_and_should_not_be_null_when_mandatory_fields_are_present() {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setTitle("Java")
                .setBody("abc def ghi")
                .setDescription("some description")
                .setTags(new String[]{"code", "student"})
                .setFeaturedImage("writerpad.com/image101.jpg")
                .build();
        ArticleService articleService = new ArticleService();
        Article article = articleService.saveArticle(articleRequest);
        Assertions.assertThat(article).isNotNull();
    }


}