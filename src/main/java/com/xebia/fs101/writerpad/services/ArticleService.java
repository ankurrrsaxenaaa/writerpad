package com.xebia.fs101.writerpad.services;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import com.xebia.fs101.writerpad.utilities.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article saveArticle(Article article) {
        article.setSlug(StringUtil.generateSlug(article.getTitle()));
        article.setUpdatedAt();
        this.articleRepository.save(article);
        return article;
    }


    public Optional<Article> update(String slugUuid, Article copyFrom) {
        Optional<Article> optionalArticle = findById(slugUuid);
        if (!optionalArticle.isPresent()) {
            return Optional.empty();
        }
        Article article = optionalArticle.get();
        article.update(copyFrom);
        return Optional.of(articleRepository.save(article));
    }

    public Optional<Article> findById(String slugID) {
        UUID id = extractId(slugID);
        return this.articleRepository.findById(id);
    }

    private UUID extractId(String slugUuid) {
        return UUID.fromString(slugUuid.substring(slugUuid.length() - 36));
    }

    public Optional<Article> findArticle(String slugUuid) {
        UUID id = UUID.fromString(slugUuid);
        return articleRepository.findById(id);

    }

    public void deleteArticle(Article article) {
        articleRepository.deleteById(article.getId());
    }

    public Page<Article> findAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
