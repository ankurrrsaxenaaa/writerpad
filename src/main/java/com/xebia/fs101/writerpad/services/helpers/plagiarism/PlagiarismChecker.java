package com.xebia.fs101.writerpad.services.helpers.plagiarism;

import com.xebia.fs101.writerpad.repository.ArticleRepository;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlagiarismChecker {
    @Autowired
    private ArticleRepository articleRepository;

    int getSimilarityScore(String src, String target) {
        SimilarityStrategy strategy = new JaroWinklerStrategy();
        StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
        return Math.multiplyExact((int) service.score(src, target), 100);
    }

    public boolean isArticlePlagiarised(String src) {
        return articleRepository.findAll().stream()
                .anyMatch(a -> getSimilarityScore(src, a.getBody()) > 70);
    }
}
