package com.xebia.fs101.writerpad.services.domain;

import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.domain.User;
import com.xebia.fs101.writerpad.exceptions.ArticleNotFoundException;
import com.xebia.fs101.writerpad.exceptions.UnauthorizedUserException;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import com.xebia.fs101.writerpad.repository.UserRepository;
import com.xebia.fs101.writerpad.utilities.ArticleStatus;
import com.xebia.fs101.writerpad.utilities.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.xebia.fs101.writerpad.utilities.StringUtil.extractId;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Article save(Article article, User user) {
        User found = userRepository.findById(user.getId()).get();
        article.setUser(found);
        article.setSlug(StringUtil.generateSlug(article.getTitle()));
        article.setUpdatedAt();
        this.articleRepository.save(article);
        return article;
    }


    public Article update(String slugUuid, Article copyFrom, User user) {
        Article article = findBySlugId(slugUuid);
        if (article.getUser().getUsername().equals(user.getUsername())) {
            throw new UnauthorizedUserException();
        }
        article.update(copyFrom);
        return articleRepository.save(article);
    }

    public Article findBySlugId(String slugID) {
        UUID id = extractId(slugID);
        Optional<Article> optionalArticle = this.articleRepository.findById(id);
        if (!optionalArticle.isPresent()) {
            throw new ArticleNotFoundException();
        }
        return optionalArticle.get();
    }

    public Optional<Article> find(String slugUuid) {
        UUID id = UUID.fromString(slugUuid);
        return this.articleRepository.findById(id);
    }

    public void delete(String slugUuid, User user) {
        Article article = findBySlugId(slugUuid);
        if (!article.getUser().getUsername().equals(user.getUsername())) {
            throw new UnauthorizedUserException();
        }
        this.articleRepository.deleteById(extractId(slugUuid));
    }

    public Page<Article> findAll(Pageable pageable) {
        return this.articleRepository.findAll(pageable);
    }

    public Page<Article> findByStatus(String status, Pageable pageable) {
        return this.articleRepository.findAllByStatus(
                ArticleStatus.valueOf(status.toUpperCase()),
                pageable);
    }

    public Article publish(Article publish) {
        publish.setStatus(ArticleStatus.PUBLISHED);
        return this.articleRepository.save(publish);
    }

    public boolean isDraft(ArticleStatus status) {
        return status == ArticleStatus.DRAFT;
    }


    @Transactional(readOnly = true)
    public Map<String, Long> getTags() {
        Stream<String> tags = articleRepository.findAllByTags();
        return tags
                .map(t -> t.trim().toLowerCase())
                .collect(groupingBy(t -> t, counting()));
    }

    public void favourite(Article found) {
        found.setFavoritesCount(found.getFavoritesCount() + 1);
        found.setFavorited(true);
        this.articleRepository.save(found);
    }

    public void unfavourite(Article found) {
        int favouriteCount = found.getFavoritesCount();
        if (favouriteCount == 1) {
            found.setFavorited(false);
        }
        if (favouriteCount != 0) {
            found.setFavoritesCount(found.getFavoritesCount() - 1);
        }
        this.articleRepository.save(found);
    }
}