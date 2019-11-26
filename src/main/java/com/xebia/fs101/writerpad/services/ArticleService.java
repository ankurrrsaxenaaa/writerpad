package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import com.xebia.fs101.writerpad.request_model.ArticleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.xebia.fs101.writerpad.utilities.StringUtil.generateSlug;
import static com.xebia.fs101.writerpad.utilities.StringUtil.generateSlugArray;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article saveArticle(ArticleRequest articleRequest) {
        Article article = new Article.Builder()
                .setTitle(articleRequest.getTitle())
                .setBody(articleRequest.getBody())
                .setDescription(articleRequest.getDescription())
                .setFeaturedImage(articleRequest.getFeaturedImage())
                .setSlug(generateSlug(articleRequest.getTitle()))
                .setTags(generateSlugArray(articleRequest.getTags()))
                .setCreatedAt(new Date())
                .setUpdatedAt(new Date())
                .setFavorited(false)
                .setFavoritesCount(0)
                .build();
        articleRepository.save(article);
        return article;
    }


    public boolean isValidArticle(ArticleRequest articleRequest) {
        if (articleRequest.getTitle() == null || articleRequest.getTitle().equals("") ||
                articleRequest.getBody() == null || articleRequest.getBody().equals("") ||
                articleRequest.getDescription() == null || articleRequest.getDescription().equals("")) {
            throw new IllegalArgumentException("The title/body/description cannot be null/empty");
        } else {
            return true;
        }
    }
}
