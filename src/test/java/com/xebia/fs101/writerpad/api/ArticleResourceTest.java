package com.xebia.fs101.writerpad.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.writerpad.api.representations.ArticleRequest;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import com.xebia.fs101.writerpad.repository.CommentRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleResourceTest {

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void mock_mvc_should_be_set() {
        Assertions.assertThat(mockMvc).isNotNull();
    }


    @Test
    void should_be_able_to_return_201_status_code_for_create_when_proper_article_request_is_given_with_required_feilds() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setBody("Hello world")
                .setDescription("Life is beautiful")
                .setTitle("Okay life!")
                .build();
        String json = objectMapper.writeValueAsString(articleRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/articles")
                .accept(MediaType.APPLICATION_JSON)
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.body").value("Hello world"))
                .andExpect(jsonPath("$.title").value("Okay life!"))
                .andExpect(jsonPath("$.description").value("Life is beautiful"))
                .andExpect(jsonPath("$.slug").value("okay-life!"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.favorited").isBoolean())
                .andExpect(jsonPath("$.favorited").value(false))
                .andExpect(jsonPath("$.favoritesCount").value(0));
    }

    @Test
    void check_api_articles_post_request_status_as_bad_request() throws Exception {

        String mockJson = "{\n" +
                "  \"description\": \"Ever wonder how?\",\n" +
                "  \"body\": \"You have to believe\",\n" +
                "  \"tags\": [\"java\", \"Spring Boot\", \"tutorial\"],\n" +
                "  \"featuredImage\": \"url of the featured image\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/articles")
                .content(mockJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_update_an_article() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setBody("abc")
                .setTitle("my blog")
                .setDescription("ankur saxena")
                .build();
        Article article = createArticle("title1", "description1", "body1");
        Article toupdate = articleRequest.toArticle();
        Article saved = this.articleRepository.save(toupdate);
        String json = objectMapper.writeValueAsString(articleRequest);
        String id = String.format("%s-%s", saved.getSlug(), saved.getId());
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/articles/{slugUuid}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("my blog"))
                .andExpect(jsonPath("$.createdAt")
                        .value(CoreMatchers.not(article.getUpdatedAt())));
    }

    @Test
    void should_list_all_articles() throws Exception {
        Article article1 = createArticle("Title1", "Description1", "body1");
        Article article2 = createArticle("Title2", "description2", "body2");
        Article article3 = createArticle("Title3", "description3", "body3");

        articleRepository.saveAll(Arrays.asList(article1, article2, article3));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/articles"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));


    }

    @Test
    void should_list_all_articles_with_pagination() throws Exception {
        Article article1 = createArticle("Title1", "Description1", "body1");
        Article article2 = createArticle("Title2", "description2", "body2");
        Article article3 = createArticle("Title3", "description3", "body3");

        articleRepository.saveAll(Arrays.asList(article1, article2, article3));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/articles?pageNo=0&pageSize=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));


    }

    private Article createArticle(String title, String description, String body) {
        return new Article.Builder()
                .setTitle(title)
                .setDescription(description)
                .setBody(body)
                .build();
    }
}