package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.api.representations.ArticleRequest;
import com.xebia.fs101.writerpad.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    /*@Test
    void should_save_Article_from_ArticleRequest_and_should_not_be_null_when_mandatory_fields_are_present() {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setTitle("Java")
                .setBody("abc def ghi")
                .setDescription("some description")
                .setTags(new String[]{"code", "student"})
                .build();
        Article toSave = articleRequest.toArticle();
        Article article = articleService.save(toSave);
        Assertions.assertThat(article).isNotNull();
    }*/
}