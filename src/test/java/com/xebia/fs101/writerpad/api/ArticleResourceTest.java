package com.xebia.fs101.writerpad.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.fs101.writerpad.api.representations.ArticleRequest;
import com.xebia.fs101.writerpad.domain.Article;
import com.xebia.fs101.writerpad.repository.ArticleRepository;
import com.xebia.fs101.writerpad.repository.CommentRepository;
import com.xebia.fs101.writerpad.services.EmailService;
import com.xebia.fs101.writerpad.utilities.ArticleStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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


    @MockBean
    EmailService emailService;

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
        mockMvc.perform(post("/api/articles")
                .accept(MediaType.APPLICATION_JSON)
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
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
    void should_be_able_to_return_201_status_code_for_create_when_proper_article_request_is_given_with_required_and_optional_feilds() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setBody("Hello world")
                .setDescription("Life is beautiful")
                .setTitle("Okay life!")
                .setTags(new String[]{
                        "okay", "nice", "yups"
                }).build();
        String json = objectMapper.writeValueAsString(articleRequest);
        mockMvc.perform(post("/api/articles")
                .accept(MediaType.APPLICATION_JSON)
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.body").value("Hello world"))
                .andExpect(jsonPath("$.title").value("Okay life!"))
                .andExpect(jsonPath("$.description").value("Life is beautiful"))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasItems("okay", "nice", "yups")))
                .andExpect(jsonPath("$.slug").value("okay-life!"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.favorited").isBoolean())
                .andExpect(jsonPath("$.favorited").value(false))
                .andExpect(jsonPath("$.favoritesCount").value(0));
    }

    @Test
    void should_be_able_to_return_400_status_code_for_create_when_any_required_feild_is_missing_is_given() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setDescription("Life is beautiful")
                .setTitle("Okay life!")
                .setTags(new String[]{
                        "okay", "nice", "yups"
                }).build();
        String json = objectMapper.writeValueAsString(articleRequest);
        mockMvc.perform(post("/api/articles")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_be_able_to_return_400_status_code_for_create_when_any_required_feild_is_empty() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setBody("")
                .setDescription("Life is beautiful")
                .setTitle("Okay life!")
                .setTags(new String[]{
                        "okay", "nice", "yups"
                }).build();
        String json = objectMapper.writeValueAsString(articleRequest);
        mockMvc.perform(post("/api/articles")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_update_an_article() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setTitle("Okay life!")
                .build();
        String json = objectMapper.writeValueAsString(articleRequest);
        Article saved = articleRepository.save(new Article.Builder()
                .setBody("abc")
                .setDescription("abc")
                .setTitle("abc")
                .build());
        mockMvc.perform(patch("/api/articles/{slug_uuid}", "abc" + "-" + saved.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Okay life!"))
                .andExpect(jsonPath("$.body").value("abc"))
                .andExpect(jsonPath("$.description").value("abc"));
    }

    @Test
    void should_be_able_to_return_404_status_code_for_patch_operation_when_id_is_wrong() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setTitle("Okay life!")
                .build();
        String json = objectMapper.writeValueAsString(articleRequest);
        Article saved = articleRepository.save(new Article.Builder()
                .setBody("abc")
                .setDescription("abc")
                .setTitle("abc")
                .build());
        mockMvc.perform(patch("/api/articles/{slug_uuid}", "abc" + "-" + saved.getId() + "abc")
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void should_list_all_articles() throws Exception {
        Article article1 = createArticle("Title1", "Description1", "body1");
        Article article2 = createArticle("Title2", "description2", "body2");
        Article article3 = createArticle("Title3", "description3", "body3");

        articleRepository.saveAll(Arrays.asList(article1, article2, article3));

        this.mockMvc.perform(get("/api/articles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));


    }

    @Test
    void should_list_all_articles_with_pagination() throws Exception {
        Article article1 = createArticle("Title1", "Description1", "body1");
        Article article2 = createArticle("Title2", "description2", "body2");
        Article article3 = createArticle("Title3", "description3", "body3");

        articleRepository.saveAll(Arrays.asList(article1, article2, article3));

        this.mockMvc.perform(get("/api/articles?pageNo=0&pageSize=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void should_save_article_in_DRAFT_mode_by_default() throws Exception {
        ArticleRequest articleRequest = new ArticleRequest.Builder()
                .setTitle("let us study java")
                .setDescription("java is a programming language")
                .setBody("java is not javascript")
                .build();
        String json = objectMapper.writeValueAsString(articleRequest);
        mockMvc.perform(post("/api/articles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("let us study java"))
                .andExpect(jsonPath("$.body").value("java is not javascript"))
                .andExpect(jsonPath("$.description").value("java is a programming language"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.tags").isEmpty())
                .andExpect(jsonPath("$.status").value("DRAFT"))
                .andExpect(jsonPath("$.favorited").isBoolean())
                .andExpect(jsonPath("$.favoritesCount").value("0"));
    }

    @Test
    void should_return_an_articles_by_status_with_id() throws Exception {
        Article article = createArticle("Title 1","Description 1","Body 1");
        article.setUpdatedAt();
        Article saved = articleRepository.save(article);
        mockMvc.perform(get("/api/articles/{slugUuid}", "great-world-" + saved.getId()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Title 1"))
                .andExpect(jsonPath("$.body").value("Body 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.status").value("DRAFT"))
                .andExpect(jsonPath("$.favorited").isBoolean())
                .andExpect(jsonPath("$.favoritesCount").value("0"));
    }

    @Test
    void should_list_all_articles_by_status_code_and_status_code_is_DRAFT() throws Exception {
        Article article = createArticle("Title 1", "Description 1", "Body 1");
        Article article2 = createArticle("Title 1", "Description 1", "Body 1");
        articleRepository.saveAll(Arrays.asList(article, article2));
        mockMvc.perform(get("/api/articles?status=DRAFT", "DRAFT"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_be_able_to_publish_the_article() throws Exception {
        Article article = createArticle("Title 1", "Description 1", "Body 1");
        Article saved = articleRepository.save(article);
        when(emailService.sendEmail(article)).thenReturn(true);
        mockMvc.perform(post("/api/articles/{slugUuid}/PUBLISH", "title-1" + saved.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_400_when_status_is_changed_to_published_of_an_already_published_article() throws Exception {
        Article article = createArticle("Title 1", "Description 1", "Body 1");
        article.setUpdatedAt();
        article.setStatus(ArticleStatus.PUBLISHED);
        Article saved = articleRepository.save(article);
        when(emailService.sendEmail(article)).thenReturn(true);
        mockMvc.perform(post("/api/articles/{sluguuid}/PUBLISH", "title-1-" + saved.getId()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private Article createArticle(String title, String description, String body) {
        return new Article.Builder()
                .setTitle(title)
                .setDescription(description)
                .setBody(body)
                .build();
    }
}