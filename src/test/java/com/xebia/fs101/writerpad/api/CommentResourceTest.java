package com.xebia.fs101.writerpad.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.writerpad.api.representations.CommentRequest;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import com.xebia.fs101.writerpad.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class CommentResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void should_add_a_comment_to_article() throws Exception {
        Article article = createArticle("Title1", "Description", "Body1");
        Article savedArticle = this.articleRepository.save(article);
        String id = String.format("%s-%s", "title1-", savedArticle.getId());
        CommentRequest commentRequest = new CommentRequest("comment");
        String json = objectMapper.writeValueAsString(commentRequest);


        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/articles/{slugUuid}/comments", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body")
                        .value("comment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ipAddress")
                        .isNotEmpty());
    }

    private Article createArticle(String title, String description, String body) {
        return new Article.Builder()
                .setTitle(title)
                .setDescription(description)
                .setBody(body)
                .build();

    }
}